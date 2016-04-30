package com.jhl.proxy.impl.jyt;

import com.jhl.event.OnChargeSuccess;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.proxy.IProxy;
import com.jhl.proxy.impl.jyt.util.StringUtil;
import com.jhl.service.AccountService;
import com.jhl.service.ChannelOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hallywu on 16/1/29.
 * 金运通资管接口实现类
 */
@Service("jytPay")
public class JytPay extends JytClientMsg{

    private static final Logger logger = LoggerFactory.getLogger(JytPay.class);
    @Autowired
    AccountService accountService;
    @Autowired
    ChannelOrderService channelOrderService;

    /** 接口名称*/
    public static String TRAN_CODE = "TC1001";
    /**代收产品类型 - 虚拟账户充值*/
    public static String BSN_CODE = "11203";
    /**账户类型 - 对私*/
    public static String ACC_TYPE = "00";
    /**证件类型 - 身份证*/
    public static String CERT_TYPE = "01";

    /**
     * version:1.0.6
     * desc:提早订单保存，将金运通同步执行结果更新至订单中，方便数据排错
     * @param bankCard
     * @param amount
     * @param account
     * @param onChargeSuccess
     * @return
     * @throws Exception
     */
    @Transactional
    public Map<String,String> charge(BankCard bankCard, Double amount, Account account, OnChargeSuccess onChargeSuccess) throws Exception{

        Map<String,String> map = new HashMap<String,String>();
        String bankName = bankCard.getBankName();
        String accountNo = bankCard.getBankCardNo();
        String accountName = account.getRealName();
        String mobile = bankCard.getMobile();
        String accountType = ACC_TYPE;
        String bsnCode = BSN_CODE;
        String certType = CERT_TYPE;
        String certNo = account.getCertNo();
        String tranFlow = getTranFlow();

        ChannelOrder channelOrder = channelOrderService.save(account.getId(),amount, ChannelOrder.Type.CHARGE,tranFlow);

        String xml = getTC1001Xml(mobile,bankName, accountNo, accountName,accountType,
                amount.toString(), bsnCode, certType, certNo,tranFlow);
        logger.info("向金运通发送充值申请开始：" + xml);
        String mac = signMsg(xml);//加签，用商户端私钥进行加签
        String respMsg = sendTranMsg(xml, mac);//接收响应报文
        String respDesc = getHeadNode(respMsg, "resp_desc");//解析响应数据
        String respCode = getHeadNode(respMsg, "resp_code");//解析响应数据
        boolean result = StringUtil.equalsIgnoreCase(respCode, IProxy.JYT_RESP_CODE_SUCCESS);
        if(result){
            channelOrder.setStatus(ChannelOrder.STATUS_SUCCESS);
            onChargeSuccess.process(amount,account);
        }else {
            channelOrder.setStatus(ChannelOrder.STATUS_FAILED);
        }
        channelOrder.setAsyResult(respDesc);
        channelOrderService.update(channelOrder);

        map.put("respCode",respCode);
        map.put("respDesc",respDesc);

        logger.info("向金运通发送充值申请结束：" + respMsg);
        logger.info("同步状态码：" + respCode);
        return map;
    }

    /**
     * 封装单笔代收TC1001报文
     * @param bankName
     * @param accountNo
     * @param accountName
     * @param accountType
     * @param tranAmt
     * @param bsnCode
     * @param certType
     * @param certNo
     * @return
     */
    public String getTC1001Xml(String mobile,String bankName,String accountNo,String accountName,String accountType,
                               String tranAmt,String bsnCode,String certType,String certNo,String tranFlow){
        StringBuffer xml = new StringBuffer();
        xml.append(getMsgHeadXml(TRAN_CODE,tranFlow));
        xml.append("<body><mer_viral_acct></mer_viral_acct>");
        xml.append("<agrt_no></agrt_no>").append("<bank_name>").append(bankName).append("</bank_name>");
        xml.append("<account_no>").append(accountNo).append("</account_no>");
        xml.append("<account_name>").append(accountName).append("</account_name>");
        xml.append("<account_type>").append(accountType).append("</account_type>");
        xml.append("<brach_bank_province></brach_bank_province>");
        xml.append("<brach_bank_city></brach_bank_city>");
        xml.append("<brach_bank_name></brach_bank_name>");
        xml.append("<tran_amt>").append(tranAmt).append("</tran_amt>");
        xml.append("<currency>").append("CNY").append("</currency>");
        xml.append("<bsn_code>").append(bsnCode).append("</bsn_code>");
        xml.append("<cert_type>").append(certType).append("</cert_type>");
        xml.append("<cert_no>").append(certNo).append("</cert_no>");
        xml.append("<mobile>").append(mobile).append("</mobile>");
        xml.append("<remark></remark>");
        xml.append("<reserve></reserve>");
        xml.append("</body></message>");
        return xml.toString();
    }

}
