package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.cache.RedisClient;
import com.jhl.constant.ConfigKey;
import com.jhl.dao.NoticesDao;
import com.jhl.oss.OssManageUtil;
import com.jhl.pojo.biz.*;
import com.jhl.util.DateUtil;
import com.jhl.util.Freemarker;
import com.jhl.util.NumberUtil;
import com.jhl.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hallywu on 16/4/7.
 */
@Service("contractGenerateService")
public class ContractGenerateService {

    String basePath = "upload/contract";
    private static final Logger logger = LoggerFactory.getLogger(ContractGenerateService.class);

    @Autowired
    NoticesDao noticesDao;

    @Autowired
    RedisClient redisClient;
    @Resource(name = "projectService")
    ProjectService projectService;
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void asyGenWithHoldingContract(Account account, BankCard bankCard, Double amount){
        if (account == null || bankCard == null || amount == 0)return;
        Runnable worker = new Runnable() {
            @Override
            public void run() {
                try {
                    genwithHoldingContract(account,bankCard,amount);
                } catch (Exception e) {
                    logger.error("生成失败:{}",e);
                }
            }
        };
        executorService.execute(worker);
    }

    public void asyGenInvestContract(InvestOrder order,Project project,Account account){
        if (order == null)return;
        Runnable worker = new Runnable() {
            @Override
            public void run() {
                try {
                    genInvestContract(order,project,account);
                } catch (Exception e) {
                    logger.error("生成失败:{}",e);
                }
            }
        };
        executorService.execute(worker);
    }

    public void genwithHoldingContract(Account account,BankCard bankCard,Double amount) throws Exception{
        logger.info("生成充值代扣协议开始:{},{},{}",account.toString(),bankCard.toString(),amount);
        Map<String,Object> root = new HashMap<String,Object>();
        try {
            String gateway = SysConfig.geteConfigByKey(ConfigKey.STATIC_WEB_GEN);
            root.put("date", DateUtil.formatNow());
            root.put("gateway", gateway);
            root.put("amount", amount);
            root.put("account", account);
            root.put("bankCard", bankCard);
            File file = new File(basePath + "/" +account.getId()+"_"+ DateUtil.formatToday() + ".html");
            Freemarker.printFile("withHolding.ftl", root, file, "contract");
            OssManageUtil.uploadFile(file,basePath + "/" + DateUtil.formatToday());
            logger.info("生成充值代扣协议结束:{}",file.getAbsolutePath());
        } catch (Exception e) {
            logger.error("生成协议失败",e);
        }
    }

    /**
     *
     * @param order 金额为分
     * @param project
     * @param account
     * @throws Exception
     */
    public void genInvestContract(InvestOrder order,Project project,Account account) throws Exception{
        if (order == null || project == null)return;
        logger.info("投资协议生成开始:{}",order.toString());
        Map<String,Object> root = new HashMap<String,Object>();
        try {
            account.parseAccout();
            String content = queryInvestContent(project.getNo());
            String gateway = SysConfig.geteConfigByKey(ConfigKey.STATIC_WEB_GEN);
            root.put("gateway", gateway);
            root.put("content", replaceContractInfo(content,account,order.getAmount() / 100));//金额转为元
            File file = new File(PathUtil.getClassPath() + basePath + "/" +order.getProId() + "/" +order.getAccId()+"_"+ order.getNo() + ".html");
            Freemarker.printFile("invest.ftl",root,file,"contract");
            OssManageUtil.uploadFile(file,basePath + "/" + DateUtil.formatToday());
            logger.info("查看投资协议结束");
        } catch (Exception e) {
            logger.error("生成协议失败",e);
        }

    }

    /**
     * 获取DB存储的协议内容
     * @param no
     * @return
     * @throws Exception
     */
    public String queryInvestContent(String no) throws Exception{
        Map<String,Object> result = noticesDao.queryContract(new Object[]{"CONTRACT",no});
        if (result!=null && result.size() > 0) {
            return (String)result.get("CONTENT");
        }else {
            return "";
        }
    }

    /**
     * 根据占位符替换协议信息
     * @param content
     * @param account
     * @param amount 单位万元
     * @return
     */

    private static final String SYMBOL_REALNAME = "${account.realName}"; //投资人
    private static final String SYMBOL_ACCNAME = "${account.accName}";//投资人金葫芦账号
    private static final String SYMBOL_CEARNO = "${account.certNo}";//身份证号
    private static final String SYMBOL_DATE = "${date}";//日期
    private static final String SYMBOL_AMOUNT = "${amount}";//投资金额

    public String replaceContractInfo(String content,Account account,double amount){
        if (Strings.isNullOrEmpty(content)){
            logger.warn("投资协议生成失败，投资数据为空:{}",account.toString());
            return content;
        }
        content = content.replace(SYMBOL_ACCNAME,account.getAccName());
        content = content.replace(SYMBOL_REALNAME,account.getRealName());
        content = content.replace(SYMBOL_CEARNO,account.getCertNo());
        content = content.replace(SYMBOL_DATE,DateUtil.formatNow());
        content = content.replace(SYMBOL_AMOUNT,Double.toString(amount /  10000));//金额转为万元
        return content;
    }

    private static String PREFIX = "supplier_";

    public SupplierProjectMapping getSupplier(String no) throws Exception{
        Object supplierProjectMapping = redisClient.get(PREFIX+no,SupplierProjectMapping.class);
            supplierProjectMapping = projectService.queryMappingSupplier(no);
//        if (supplierProjectMapping == null) {
//            if(supplierProjectMapping == null) return null;
//            redisClient.expireSet(PREFIX + no,supplierProjectMapping,60 * 60 * 24);//24小时内缓存有效
//        }
        return (SupplierProjectMapping)supplierProjectMapping;
    }

}
