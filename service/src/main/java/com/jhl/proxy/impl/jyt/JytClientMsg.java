package com.jhl.proxy.impl.jyt;

import com.jhl.constant.ConfigKey;
import com.jhl.exception.AppException;
import com.jhl.proxy.impl.jyt.util.*;
import com.jhl.service.SysConfig;
import com.jhl.util.HttpClient431Util;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Assert;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hallywu on 16/1/29.
 * 金运通连接工具类
 */
public class JytClientMsg {

    protected static final Logger log = Logger.getLogger(JytClientMsg.class);

    //测试环境测试商户
    protected static String MERCHANT_ID = "584060100003";	//替换为自己的商户号

    protected static String RESP_MSG_PARAM_SEPARATOR = "&";

    /**返回报文merchant_id字段前缀*/
    protected static String RESP_MSG_PARAM_PREFIX_MERCHANT_ID = "merchant_id=";

    /**返回报文xml_enc字段前缀*/
    protected static String RESP_MSG_PARAM_PREFIX_XML_ENC = "xml_enc=";
    /**返回报文xml_enc字段前缀*/
    protected static String RESP_MSG_PARAM_PREFIX_KEY_ENC = "key_enc=";

    /**返回报文sign字段前缀*/
    protected static String RESP_MSG_PARAM_PREFIX_SIGN = "sign=";

    //参数初始化
//    public void init(){
//        MERCHANT_ID = SysConfig.geteConfigByKey(ConfigKey.JYT_TRAN_URL);
//        MERCHANT_ID = SysConfig.geteConfigByKey(ConfigKey.JYT_TRAN_URL);
//        MERCHANT_ID = SysConfig.geteConfigByKey(ConfigKey.JYT_TRAN_URL);
//    }

    protected static RSAHelper rsaHelper = new RSAHelper();
    {
        try {
            rsaHelper.initKey(JytConfig.CLIENT_PRIVATE_KEY, JytConfig.SERVER_PUBLIC_KEY, 2048);
        } catch (Exception e) {
            log.error("充值加密初始化失败！",e);
        }
    }

    /**
     * 获取交易流水号
     * <p> Create Date: 2014-5-10 </p>
     * @return
     */
    protected static String getTranFlow(){
        return MERCHANT_ID+ RandomStringUtils.randomNumeric(18);
    }


    /**
     * 获得报文头字符串
     * <p> Create Date: 2014-5-10 </p>
     * @param tranCode
     * @return
     */
    protected static String getMsgHeadXml(String tranCode,String tranFlow){
        StringBuffer headXml = new StringBuffer();
        headXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><message><head><version>1.0.0</version>");
        headXml.append("<tran_type>01</tran_type>")
                .append("<merchant_id>").append(MERCHANT_ID).append("</merchant_id>");
        headXml.append("<tran_date>").append(DateTimeUtils.getNowDateStr(DateTimeUtils.DATE_FORMAT_YYYYMMDD))
                .append("</tran_date>");
        headXml.append("<tran_time>").append(DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_HHMMSS))
                .append("</tran_time><tran_flowid>").append(tranFlow)
                .append("</tran_flowid><tran_code>").append(tranCode).append("</tran_code>");
        headXml.append("</head>");

        return headXml.toString();
    }

    protected static String sendAuthMsg(String xml,String sign) throws Exception{
        String url = SysConfig.geteConfigByKey(ConfigKey.JYT_AUTH_URL);
        return sendMsg(xml, sign, url);
    }

    public String sendTranMsg(String xml,String sign) throws Exception{
        String url = SysConfig.geteConfigByKey(ConfigKey.JYT_TRAN_URL);
        return sendMsg(xml,sign,url);
    }

    private static String sendMsg(String xml,String sign,String url) throws Exception{
        log.info("上送报文："+xml);
        log.info("上送签名："+sign);

        byte[] des_key = DESHelper.generateDesKey() ;

        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("merchant_id", MERCHANT_ID);
        paramMap.put("xml_enc", encryptXml(xml,des_key));
        paramMap.put("key_enc", encryptKey(des_key));
        paramMap.put("sign", sign);

        // 获取执行结果

        String res = HttpClient431Util.doPost(paramMap, url);

        if(res  == null){
            log.error("服务器连接失败");

            throw new AppException("测试异常");
        }else{
            log.info("连接服务器成功,返回结果"+res);
        }
        String []respMsg = res.split(RESP_MSG_PARAM_SEPARATOR);

        String merchantId = respMsg[0].substring(RESP_MSG_PARAM_PREFIX_MERCHANT_ID.length());
        String respXmlEnc = respMsg[1].substring(RESP_MSG_PARAM_PREFIX_XML_ENC.length());
        String respKeyEnc   = respMsg[2].substring(RESP_MSG_PARAM_PREFIX_KEY_ENC.length());
        String respSign = respMsg[3].substring(RESP_MSG_PARAM_PREFIX_SIGN.length());

        byte respKey[] = decryptKey(respKeyEnc) ;

        String respXml = decrytXml( respXmlEnc,respKey ) ;


        log.info("返回报文merchantId:"+merchantId);
        log.info("返回报文XML:"+respXml);
        log.info("返回报文签名:"+respSign);

        Assert.isTrue(verifyMsgSign(respXml, respSign), "返回报文校验失败");

        return respXml;
    }

    protected static String getHeadNode(String respMsg, String headNode) throws Exception{
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));

        //解析报文头
        Node packageHead = doc.selectSingleNode("/message/head");

        Node respCodeNode = packageHead.selectSingleNode(headNode);

        return respCodeNode.getText();
    }

    public static void main(String[] args) throws Exception {
//        String result = "merchant_id=584060100002&xml_enc=637ca100b58d12a5c5d3025c74a59e9501791fe8cc114a54f8d29d1ed27ee7ad7eff44212fe8c2d409489ec9b526e47ba620cd6eb285c01743819cd0979623f815641490c43942e44c24dbda40c931c96a4dee458cf82061b0eca4c9eaaff6a5249e3f71eb250d28fc112ca55a3d3debd0eb5acaba41739f11d886020e2f7317edb22d8c73c402ed57a6ae205bff6e4949e1334b99dee8f043903dddfc05a04afbcd69a414ab2f5746640b5210520b43840ca721c6e5c94e4e06e17972f034479a73d20f4e9548d60146f10d21a49e2c16876103686913f6f810b397e72a6642e483e956b1f6ec220eb65877feffa9cab5219d2cee1c0786c79d884d0bb3eb31d6fb5b054fbda7a35a3a1d8d9bf298ae17c8461e6cf2777a2f939cb8056aafddbafdca77ee5da99c0b3ebff5816a2e258e1b4bf0d7dc7e11bcac2378b0bdb19352b83146505ab4b466dc02d5b3fd87bd&key_enc=74155c212fa70e056d3d5027513f9515e981502116e2e13f52277101a2016555931eed50cf08d58b98dfd646f485b4564889eb7719ba369b12bc1723f27e32a9c22f40cb5eda20edd51caf22f0bd2a0da6496e040902a533da9973f76f97fee29ba650cc0443a246696b6e15fe3bf780985149ce31d98e9a41cb040501fe652f7c60a4c1ee097edd9ae101fbbcac08e752b8a06d9040c4191f6452bea8670e5ea45aa95b3cfca5620e4c3e3f0f1c63bd85a9cb2c93c5a0dc4a2ef1c838156eab6aa146f884f8e00094ad2b49f4142dfbf79b7ae7e22636e7182fddfd7332261f2b47fcbbf03d4fb803e62a68ca1ed46996bca3b7f3fdc6d2d9759e6b5e756732&sign=381e66ef277c45544a99d60c4adc22abc61181cb53e1ebeaf005271ea943d7d0f7451b245ff75a5f2979cc93aa22a6af20aaac9bf8f682ceee9f20a515da12d44bd2d84185e6fee58b37789b4a0ecf748bad4750a7db345358dfba9a1cd30c1d460f0c3a6b5a1b0daa50ec7260c3e7efb82005c29f3ac22d1f1af50f502480be6b277d4819b1aa5de9a5cfd0d241a12b4d21ebdf90a1fb71c95c33dbd9e9ace5ce51bceb1e74b05aef4556b757d0e00a6a6d9f87347819d30cbac1d460ebc8b4577c425803b61b93d258774b01a13f7501dc73bf6116b505d70c0c9d09fa7a4feb83e7450737b70f513addfac53ec7c56b161b01f2efa9e201c07c1895517edd";
//        String []respMsg = result.split(RESP_MSG_PARAM_SEPARATOR);
//        rsaHelper.initKey(JytConfig.CLIENT_PRIVATE_KEY, JytConfig.SERVER_PUBLIC_KEY, 2048);
//        String merchantId = respMsg[0].substring(RESP_MSG_PARAM_PREFIX_MERCHANT_ID.length());
//        String respXmlEnc = respMsg[1].substring(RESP_MSG_PARAM_PREFIX_XML_ENC.length());
//        String respKeyEnc   = respMsg[2].substring(RESP_MSG_PARAM_PREFIX_KEY_ENC.length());
//        String respSign = respMsg[3].substring(RESP_MSG_PARAM_PREFIX_SIGN.length());
//        byte respKey[] = decryptKey(respKeyEnc) ;
//
//        String respXml = decrytXml( respXmlEnc,respKey ) ;
//
//
//        log.info("返回报文merchantId:"+merchantId);
//        log.info("返回报文XML:"+respXml);
//        log.info("返回报文签名:"+respSign);
    }

    protected static String getBodyNode(String respMsg, String bodyNode) throws Exception{
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));

        //解析报文头
        Node packageHead = doc.selectSingleNode("/message/body");
        if(packageHead==null)
            return null;

        Node tranStateNode = packageHead.selectSingleNode(bodyNode);
        if(tranStateNode==null)
            return null;

        return tranStateNode.getText();
    }


    protected static String encryptKey( byte[] key){

        byte[] enc_key = null ;
        try {
            enc_key = rsaHelper.encryptRSA(key, false, "UTF-8") ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return StringUtil.bytesToHexString(enc_key) ;
    }

    protected static byte[] decryptKey(String hexkey){
        byte[] key = null ;
        byte[] enc_key = StringUtil.hexStringToBytes(hexkey) ;

        try {
            key = rsaHelper.decryptRSA(enc_key, false, "UTF-8") ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key ;
    }

    private static String encryptXml( String xml, byte[] key){

        String enc_xml = CryptoUtils.desEncryptToHex(xml, key) ;

        return enc_xml;
    }

    protected static String decrytXml(String xml_enc, byte[] key) {
        String xml = CryptoUtils.desDecryptFromHex(xml_enc, key) ;
        return xml;
    }

    protected static boolean verifyMsgSign(String xml, String sign){
        byte[] bsign = StringUtil.hexStringToBytes(sign) ;

        boolean ret = false ;
        try {
            ret = rsaHelper.verifyRSA(xml.getBytes("UTF-8"), bsign, false, "UTF-8") ;
        } catch (Exception e) {
            log.error(e);
        }

        return ret;
    }

    protected static String signMsg( String xml ){
        String hexSign = null ;

        try {
            byte[] sign = rsaHelper.signRSA(xml.getBytes("UTF-8"), false, "UTF-8") ;

            hexSign = StringUtil.bytesToHexString(sign) ;
        }  catch (Exception e) {
            log.error(e);
        }

        return hexSign;
    }

    //验签
    protected static boolean verifySign(byte[] plainBytes, byte[] signBytes){
        boolean flag = false;
        try {
            flag = rsaHelper.verifyRSA(plainBytes, signBytes, false, "UTF-8");
        } catch (Exception e) {
            log.error(e);
        }
        return flag;
    }



}
