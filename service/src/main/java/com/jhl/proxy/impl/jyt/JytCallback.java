package com.jhl.proxy.impl.jyt;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.jhl.exception.AppException;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.proxy.ICallback;
import com.jhl.proxy.IProxy;
import com.jhl.proxy.impl.jh.JhAccontDetail;
import com.jhl.service.AccountService;
import com.jhl.service.BankCardService;
import com.jhl.service.ChannelOrderService;
import com.jhl.service.MessageService;
import com.jhl.util.Money;
import com.jhl.util.SeqNoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/2/17.
 */
@Service("jytCallback")
public class JytCallback extends JytClientMsg implements ICallback {

    @Autowired
    ChannelOrderService channelOrderService;
    @Autowired
    AccountService accountService;
    @Resource(name = "messageService")
    MessageService messageService;
    @Autowired
    BankCardService bankCardService;
    private static final Logger logger = LoggerFactory.getLogger(JytCallback.class);
    @Override
    public void callback(JSONObject callbackPara) throws Exception {
        if (callbackPara == null)return;
        ChannelOrder channelOrder = channelOrderService.queryChannelOrderByTransflow(
                callbackPara.getString("ori_tran_flowid"));
        if(channelOrder == null){
            logger.warn("回调订单不存在："+callbackPara.toJSONString());
            return;
        }
        if(channelOrder.getStatus() != ChannelOrder.STATUS_SUCCESS){
            logger.warn("回调订单状态异常："+callbackPara.toJSONString());
            return;
        }
        Double cbAmount = Double.parseDouble(callbackPara.getString("tran_amt"));
        Assert.isTrue(new Money(cbAmount).compareTo(Money.centToYuan(channelOrder.getAmount())) == 0);
        if(IProxy.JYT_RESP_CODE_SUCCESS.equalsIgnoreCase(callbackPara.getString("tran_resp_code"))){
            channelOrder.setAsyResult(callbackPara.get("tran_state").toString());
            channelOrder.setNotifyTime(new Date());
            channelOrderService.callback(channelOrder);
            channelOrder.setStatus(ChannelOrder.STATUS_TRANSACTION_SUCCESS);
            //成功
            sendSuccMsg(channelOrder);
        }else {
            //失败，失败原因：callbackPara.get("tran_state").toString()
            channelOrder.setAsyResult(callbackPara.get("tran_state").toString());
            channelOrder.setStatus(ChannelOrder.STATUS_FAILED);
            sendFailMsg(channelOrder);
        }
    }

    private void sendSuccMsg(ChannelOrder channelOrder) {
        try {
            double amount = channelOrder.getAmount() / 100;
            if(channelOrder.getType() == ChannelOrder.Type.CHARGE.getValue()) {
                messageService.sendRechargeSuccess(amount,"在线支付",accountService.queryById(channelOrder.getUserId()));
            }else if(channelOrder.getType() == ChannelOrder.Type.CASH.getValue()) {
                messageService.sendCashSuccess(amount,channelOrder.getUserId());
            }
        } catch (Exception e) {
            logger.error("消息发送失败",e);
        }
    }

    private void sendFailMsg(ChannelOrder channelOrder) {
        try {
            double amount = channelOrder.getAmount() / 100;
            String type = "";
            if(channelOrder.getType() == ChannelOrder.Type.CHARGE.getValue()) {
                type = "充值";
            }else if(channelOrder.getType() == ChannelOrder.Type.CASH.getValue()) {
                type = "提现";
            }
            messageService.sendFail(amount,type,accountService.queryById(channelOrder.getUserId())
                    ,channelOrder.getAsyResult(),channelOrder.getOrderNo());
        } catch (Exception e) {
            logger.error("发送失败:{},{}",e,channelOrder.toString());
        }
    }

    @Override
    public void jhTransaction(String res) throws Exception {
        JhAccontDetail jhAccontDetail = new JhAccontDetail();
        JSONObject jsonObject = jhAccontDetail.parseResp(res);
        List<JSONObject> detailJsonList = jsonObject.get("DETAILLIST") == null ? null : (List<JSONObject>)jsonObject.get("DETAILLIST");
        if (detailJsonList == null){
            logger.error("解析数据失败："+jsonObject.toJSONString());
            throw new AppException("建行数据解析失败");
        }
        for (JSONObject result : detailJsonList) {
             logger.info("建行转账增加用户余额开始："+result.toJSONString());
            if (callbackCheck(result)){
                String bankNo = result.getString("ACCNO2");
                String accName = result.getString("ACC_NAME1");
                BankCard bankCard = bankCardService.queryCardByNo(bankNo);
                if (bankCard == null){
                    logger.warn("解析到备注为转账充值的数据，但用户的银行卡不存在:"+bankNo);
                    continue;
                }
                if (!bankCard.getName().equalsIgnoreCase(accName)){
                    logger.warn("解析到备注为转账充值的数据，但用户的账户名称不匹配:"+accName);
                    continue;
                }
                channelOrderService.callback_ccb(buildJhChannelOrder(result,bankCard));
                messageService.sendRechargeSuccess(result.getString("AMT"),"转账",
                        accountService.queryById(bankCard.getUserId()));
                logger.info("建行转账成功，增加用户余额成功....");
            }else{
                logger.warn("建行转账处理完成，校验数据失败，不执行转账：{}",result.toJSONString());
            }
        }

    }

    private boolean callbackCheck(JSONObject result) throws Exception{
//        if (!SysConfig.geteConfigByKey(ConfigKey.JH_TRANS_COMMENT).equals(result.getString("DET")))return false;
        String tranFlow = result.getString("TRAN_FLOW");
        if(Strings.isNullOrEmpty(tranFlow))return false;
        Integer count = channelOrderService.queryChannelOrder(tranFlow);
        if (count > 0) return false;
        if(Strings.isNullOrEmpty(result.getString("ACCNO2")))return false;
        if(Strings.isNullOrEmpty(result.getString("ACC_NAME1")))return false;
        if(Strings.isNullOrEmpty(result.getString("AMT")))return false;
        return true;
    }

    private ChannelOrder buildJhChannelOrder(JSONObject result,BankCard bankCard){
        String amount = result.getString("AMT");
        ChannelOrder channelOrder = new ChannelOrder();
        channelOrder.setAmount(new Money(Double.parseDouble(amount)).getCent());
        channelOrder.setAsyResult(result.getString("MESSAGE"));
        channelOrder.setOrderNo(SeqNoUtil.nextSeqNo(SeqNoUtil.PREFIX_FARM_CHANNEL));
        channelOrder.setBankId(bankCard.getId());
        channelOrder.setChannel(1);
        channelOrder.setStatus(ChannelOrder.STATUS_SUCCESS);
        channelOrder.setNotifyTime(new Date());
        channelOrder.setDeleteState(1);
        channelOrder.setCreateTime(new Date());
        channelOrder.setUserId(bankCard.getUserId());
        channelOrder.setTranFlow(result.getString("TRAN_FLOW"));
        channelOrder.setType(ChannelOrder.Type.CHARGE.getValue());
        return channelOrder;
    }

    @Override
    public JSONObject parseReqParameter(HttpServletRequest request) throws Exception {
        String xml_enc = request.getParameter("xml_enc");
        String key_enc = request.getParameter("key_enc");
        String sign = request.getParameter("sign");
        logger.info("金运通回调开始：{}|***|{}|***|{}",xml_enc,key_enc,sign);
        byte key[] = decryptKey(key_enc) ;
        //des解密获取xml报文
        String xml = decrytXml(xml_enc,key) ;
        logger.info("金运通解密回调数据：{}",xml);
        Assert.isTrue(verifyMsgSign(xml, sign),"金运通验证签名失败！");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tran_resp_code",getBodyNode(xml,"tran_resp_code"));
        jsonObject.put("tran_resp_desc",getBodyNode(xml,"tran_resp_desc"));
        jsonObject.put("tran_state",getBodyNode(xml,"tran_state"));
        jsonObject.put("tran_amt",getBodyNode(xml,"tran_amt"));
        jsonObject.put("ori_tran_flowid",getBodyNode(xml,"ori_tran_flowid"));
        return jsonObject;
    }
}
