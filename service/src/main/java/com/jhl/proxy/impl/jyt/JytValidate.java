package com.jhl.proxy.impl.jyt;

import com.jhl.dto.AccountDto;
import com.jhl.proxy.IProxy;
import com.jhl.proxy.impl.jyt.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/1/26.
 * 金运通实名认证实现类
 */
public class JytValidate extends JytClientMsg {

    private static final Logger logger = LoggerFactory.getLogger(JytValidate.class);
    public static String TRAN_CODE = "TR4002";//交易请请求码

    public static String validate(AccountDto account) throws Exception {
        String tranFlow = getTranFlow();

        String xml= buildReqXml(account.getCertNo(),account.getRealName(),tranFlow);
        logger.info("开始实名认证：" + xml);
        String mac=signMsg(xml);//报文加签
        String respXml = sendAuthMsg(xml, mac);//向服务器发送数据
        String respCode = getHeadNode(respXml, "resp_code");//解析响应数据
        String resp_desc = getHeadNode(respXml, "resp_desc");//解析响应数据
        logger.info("结束实名认证：" + respXml);
        logger.info("实名认证响应状态码："+respCode);
        if(StringUtil.equalsIgnoreCase(IProxy.JYT_RESP_CODE_SUCCESS,respCode)){
            return IProxy.JYT_RESP_CODE_SUCCESS;
        }
        return resp_desc;
    }

    /**
     * 获得SC0001的上送报文字符串
     * <p> Create Date: 2014-5-10 </p>
     * @return
     */
    private static String buildReqXml(String idNum,String idName,String tranFlowNo){
        StringBuffer xml = new StringBuffer();
        xml.append(getMsgHeadXml(TRAN_CODE,tranFlowNo));
        xml.append("<body><id_num>").append(idNum).append("</id_num><id_name>").append(idName).append("</id_name>");
        xml.append("</body></message>");
        return xml.toString();
    }

}
