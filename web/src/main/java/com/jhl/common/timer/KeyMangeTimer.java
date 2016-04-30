package com.jhl.common.timer;

import com.jhl.util.RSAUtils;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Created by vic wu on 2015/8/14.
 * 定时更新
 */
@Component
public class KeyMangeTimer {

    private static Logger logger = LoggerFactory.getLogger(KeyMangeTimer.class);

    private static Map<String, Object> keyMap;

    static {
        refreshKey();
    }

    /**
     * 每30分钟更新一次公钥
     */
//    @Scheduled(cron = "0 0/30 * * * ?")
    public synchronized static void refreshKey(){
        try {
            keyMap = RSAUtils.genKeyPair();
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "update public key failed,caused by {}",e);
        }
    }

    public synchronized static String getPubKey() throws Exception{
       return RSAUtils.getPublicKey(keyMap);
    }

    public synchronized static String getPriKey() throws Exception{
       return RSAUtils.getPrivateKey(keyMap);
    }
}