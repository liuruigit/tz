package com.jhl.security;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.ExecutionError;
import com.jhl.cache.SessionCache;
import com.jhl.util.SpringContextHolder;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.jhl.pojo.biz.Account;
import com.jhl.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by Hally on 2016/1/21.
 * Json Web Token管理类
 */
@Component(value = "jwtHolder")
public class JwtHolder {

    @Autowired
    SessionCache sessionCache;
    private static Logger logger = LoggerFactory.getLogger(JwtHolder.class);
    public static JwtHolder _instance(){
        return SpringContextHolder.getBean("jwtHolder");
    }

    /** 加密秘钥 */
    private byte[] sharedSecret = null;

    private String key = Base64Util.getBASE64("ji88SJuqm8n88SAqhu88lu");

    /**
     * 初始化秘钥，并保存到数据库中
     */
    public void initSecret(){
//        //先从DB库load
//        if (sharedSecret == null) {
//            SecureRandom random = new SecureRandom();
//            sharedSecret = new byte[32];
//            random.nextBytes(sharedSecret);
//            //保存sharedSecret到数据库
//        }
        sharedSecret = "2owbH3RMjRnhedqXnCiyWq3psIzeqLQypNfgUCa1KsSQaua2mLQHgkTZc1ka5uwirexCpalhPX+y".getBytes();
    }

    /**
     * 生成Json Web Token
     * @param account
     * @return
     * @throws Exception
     */
    public String genToken(Account account) throws Exception{
        System.out.println(key.getBytes(Charset.forName("UTF-8")).length);
        JWSSigner signer = new MACSigner(key);
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        builder.subject(account.getId()+"");
//        builder.issuer("https://c2id.com");//数据库配置
//        builder.expirationTime(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));//一天过期
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), builder.build());
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    public String genPayToken(Account account) throws Exception{
        JWSSigner signer = new MACSigner(key);
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        builder.subject(account.getId()+ account.getAccName());
//        builder.issuer("https://c2id.com");//数据库配置
        builder.expirationTime(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));//天过期
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), builder.build());
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    /**
     * token解析
     * @param token
     * @return
     * @throws Exception
     */
    private String tokenParse(String token) throws Exception {
//        logger.info("登陆token==========>{}",token);
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(key);
        Assert.isTrue(signedJWT.verify(verifier));
        return signedJWT.getJWTClaimsSet().getSubject();
    }

    /**
     * 判断是否过期
     * @param payToken
     * @return
     * @throws Exception
     */
    public boolean isOutOfDate(String payToken) throws Exception{
        if (Strings.isNullOrEmpty(payToken))return false;
        long now = System.currentTimeMillis();
        SignedJWT signedJWT = SignedJWT.parse(payToken);
        JWSVerifier verifier = new MACVerifier(key);
        if (!signedJWT.verify(verifier))return false;
        long time = signedJWT.getJWTClaimsSet().getExpirationTime().getTime();
        return now < time;
    }


    /**
     * token验证
     * @param token
     * @return
     * @throws Exception
     */
    public Account verifyToken(String token) throws Exception {
        if(Strings.isNullOrEmpty(token)) return null;
        String id = tokenParse(token);
        Account account = sessionCache.getCache(Integer.parseInt(id));
        if (account == null) return null;
        Assert.isTrue(token.equalsIgnoreCase(account.getToken()));
        return account;
    }

}
