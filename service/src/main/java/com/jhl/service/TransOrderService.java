package com.jhl.service;

import com.jhl.dao.InvestOrderDao;
import com.jhl.dao.TransOrderDao;
import com.jhl.exception.AppException;
import com.jhl.exception.AppExceptionType;
import com.jhl.pojo.biz.InvestOrder;
import com.jhl.pojo.biz.TransOrder;
import com.jhl.util.SeqNoUtil;
import com.jhl.common.InvestStatus;
import com.jhl.pojo.biz.TransOrderRule;
import com.jhl.util.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by martin on 2016/2/3.
 */
@Service
@Transactional
public class TransOrderService extends BaseService implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(TransOrderService.class);

    //#平台收取费率方式，0固定，1百分比
    @Value("${service_fee_type}")
    private int serviceFeeType;

    //#平台费率
    @Value("${service_fee}")
    private int serviceFee;

    //#最小持有期限天
    @Value("${limit_day}")
    private int limitDay;

    //#最小收益率（百分比）
    @Value("${min_rate}")
    private int minRate;

    //#实际收益大于最小收益,是否补差
    @Value("${payback_if_not_enough}")
    private int paybackIfNotEnough;

    //#折扣定义,逗号隔开,单位百分比,如95,为95%折
    @Value("${discounts}")
    private String discounts;

    @Autowired
    InvestOrderDao investOrderDao;

    @Autowired
    TransOrderDao transOrderDao;

    //投资策划定义，返回前端用的
    TransOrderRule transOrderRule;

    @Autowired
    AccountService accountService;

    /**
     * 查询可以转让的订单：
     * 按照最小持有天数进行查询
     * @return
     */
    public List<InvestOrder> queryValidTransOrder(Integer userId ){
        List<InvestOrder> investOrders = investOrderDao.queryValidTransOrder(userId, limitDay);
        return investOrders;
    }

    /**
     * 查询转让投资规则
     * @return
     */
    public TransOrderRule queryTransOrderRule(){
        return transOrderRule;
    }

    /**
     * 投资转让订单
     */
    public void investTransOrder(Integer accId , String accName , Integer transOrderId ) throws Exception {
        TransOrder transOrder = transOrderDao.queryById(TransOrder.class, transOrderId);
        InvestOrder investOrder = investOrderDao.queryById(InvestOrder.class , transOrder.getInvestOrderId());

        //@TODO 根据投资预期收益，结算收益


        //更新用户余额
        //accountService.updateUserBalance(accId , new Money(transOrder.getTransAmount()) , BalanceChangeType.INVEST);

        logger.info("更新订单状态");
        updateTransOrderStatus(accId , accName , transOrder);
        updateInvestOrderStatus(investOrder);
    }

    /**
     * 更新invest订单状态为已转让
     * @param investOrder
     * @throws Exception
     */
    private void updateInvestOrderStatus(InvestOrder investOrder) throws Exception {
        investOrder.setStatus(InvestStatus.TRANSFER_FINISH.getValue());
        investOrderDao.update(investOrder);
    }

    /**
     * 更新转换订单状态为成功
     * @param accId
     * @param accName
     * @param transOrder
     * @throws Exception
     */
    private void updateTransOrderStatus(Integer accId, String accName, TransOrder transOrder) throws Exception {
        transOrder.setAccName(accName);
        transOrder.setAccId(accId);
        transOrder.setStatus(InvestStatus.APPLYING.getValue());
        transOrderDao.update(transOrder);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        transOrderRule = new TransOrderRule();
        transOrderRule.setDiscounts(discounts.split(","));
        transOrderRule.setLimitDay(limitDay);
        transOrderRule.setMinRate(minRate);
        transOrderRule.setPaybackIfNotEnough(paybackIfNotEnough);
        transOrderRule.setServiceFee(serviceFee);
        transOrderRule.setServiceFeeType(serviceFeeType);
    }

    /**
     * 创建转让订单
     * 设置折扣费率,生成转让订单信息
     */
    public void createTransOrder(Integer investOrderId, String discount , double transAmount) throws Exception {
        Money investMoney = new Money(transAmount);

        InvestOrder investOrder = investOrderDao.queryById(InvestOrder.class, investOrderId);

        validDiscountAndAmount(investOrder , discount , transAmount);

        TransOrder transOrder = buildTransOrder(investOrder, discount, investMoney);

        logger.info("开始创建转让订单");
        investOrderDao.add(transOrder);

        logger.info("修改原订单状态为转让中");
        investOrder.setStatus(InvestStatus.TRANSFER.getValue());
        investOrderDao.update(investOrder);
    }

    /**
     * 检查折扣是否存在，检查转入金额是否正确
     * @param order
     * @param discount
     * @param transAmount
     * @param transAmount
     */
    private void validDiscountAndAmount(InvestOrder order , String discount , double transAmount){
        //1.检查折扣
        boolean validDiscount = false;
        for(String dis : transOrderRule.getDiscounts()){
            if( discount.equals(dis)){
                validDiscount = true ;
            }
        }

        if(!validDiscount)
            throw new AppException(AppExceptionType.TRANSFER_DISCOUNT_NOT_EXISTS);

        //2.检查转让金额
        double amount = order.getAmount() * Double.parseDouble(discount) / 100.0;
        if(Double.compare(amount , transAmount) != 0){
            throw new AppException(AppExceptionType.TRANSFER_AMOUNT_ERROR);
        }
    }

    //构造订单
    private TransOrder buildTransOrder(InvestOrder invest , String discount , Money transAmount){
        TransOrder order = new TransOrder();

        order.setInvestOrderId(invest.getId());
        order.setAmount(invest.getAmount());
        order.setProId(invest.getProId());
        order.setCreateTime(new Date());
        order.setDiscount(Integer.parseInt(discount));
        order.setTransAmount(transAmount.getCent());
//        order.setStatus(InvestStatus.INIT.getValue());
        order.setContractNo(SeqNoUtil.nextSeqNo(SeqNoUtil.PREFIX_FARM_TRANSFER));
        return order ;
    }
}
