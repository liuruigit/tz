package com.jhl.proxy.impl.jyt;

import com.jhl.pojo.biz.Account;
import com.jhl.proxy.IProxy;
import com.jhl.proxy.impl.jyt.util.StringUtil;
import com.jhl.service.BankCardService;
import com.sun.javafx.collections.MappingChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/26.
 * 金运通银行卡绑定实现类
 */
@Component()
public class JytBinding extends JytClientMsg {

    @Autowired
    private BankCardService bankCardService;
    private static final Logger logger = LoggerFactory.getLogger(JytBinding.class);
    public static String TRAN_CODE = "TR4003";//交易请请求码

    public Map<String,String> binding(String bankNo, String mobile, String type, Account account) throws Exception {
        Map<String,String> map = new HashMap<String,String>();
        String clinet = StringUtil.equalsIgnoreCase(type,"app")?"01":"02";
        String bankCode="";
        String bankCarkType = "D";//D 借记 C 贷记 A 全部
        String tranFlow = getTranFlow();

        String xml= getSc0001Xml(bankNo,bankCode,account.getCertNo(),
                account.getRealName(),clinet,bankCarkType,mobile,tranFlow);
        logger.info("开始银行卡绑定：" + xml);
        String mac=signMsg(xml);//报文加签
        String respXml = sendAuthMsg(xml, mac);//向服务器发送数据
        String respCode = getHeadNode(respXml, "resp_code");//解析响应数据
        String resp_desc = getHeadNode(respXml, "resp_desc");//解析响应数据
        String bankName = getBodyNode(respXml, "bank_name");//解析响应数据
        logger.info("结束开始银行卡绑定：" + respXml);
        logger.info("银行卡绑定状态码："+respCode);
        boolean result = StringUtil.equalsIgnoreCase(IProxy.JYT_RESP_CODE_SUCCESS,respCode);
        if(result){
            map.put("result",IProxy.JYT_RESP_CODE_SUCCESS);
            map.put("bankName",bankName);
            bankCardService.bindBankCard(bankNo,mobile,bankName,account);
            return map;
        }
        map.put("result",resp_desc);
        return map;
    }

    /**
     * 获得SC0001的上送报文字符串
     * <p> Create Date: 2014-5-10 </p>
     * @return
     */
    private String getSc0001Xml(String bankCardNo,String bankCode,String idNum,String idName,String terminalType
            ,String bankCarkType,String mobile,String tranFlowNo){
        StringBuffer xml = new StringBuffer();
        xml.append(getMsgHeadXml(TRAN_CODE,tranFlowNo));
        xml.append("<body><bank_card_no>").append(bankCardNo).append("</bank_card_no><bank_code>").append(bankCode).append("</bank_code>");
        xml.append("<id_num>").append(idNum).append("</id_num><id_name>").append(idName).append("</id_name>");
        xml.append("<terminal_type>").append(terminalType).append("</terminal_type>");
        xml.append("<bank_card_type>").append(bankCarkType).append("</bank_card_type>");
        xml.append("<phone_no>").append(mobile).append("</phone_no>");
        xml.append("</body></message>");
        return xml.toString();
    }

}
