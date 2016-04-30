package com.jhl.security;


import com.jhl.util.DESUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vic
 * Date: 13-5-26
 * Time: 下午5:11
 * 解密数据库配置文件
 */
public class PropertyDecipher extends PropertyPlaceholderConfigurer {
    //key过滤数组
    String[] filter = new String[]{"jdbc.username","jdbc.password"};
    //DES解密密钥
    private static final String keyStr = "sz7road.deci.u21msax8asdj";

    private static HashMap<String,String> ctxProMap = new HashMap<String, String>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        DESUtil.setKey(keyStr);
        Set<Object> keySet = props.keySet();
        for(Object str: keySet){
            String keyStr = str.toString();
            for(int i = 0; i < filter.length ; i++ ){
                if(keyStr.indexOf(filter[i]) != -1) {
                    //解密处理
                    String desStr = DESUtil.getDesString(props.getProperty(keyStr));
                    props.setProperty(keyStr, desStr);
                }else{//不加密的直接load到内存中缓存
                    ctxProMap.put(keyStr,props.getProperty(keyStr));
                }
            }
        }
        //解析非加密properties
        super.processProperties(beanFactory, props);
    }

    public static String getContextPropertyValue(String key){
        return ctxProMap.get(key);
    }
}
