package com.jhl.service.biz;

import com.jhl.constant.ConfigKey;
import com.jhl.dao.BaseDaoSupport;
import com.jhl.entity.Page;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.proxy.impl.jyt.JytProxy;
import com.jhl.util.PageData;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service("msChannelorderService")
public class ChannelOrderService {

    Logger logger = LoggerFactory.getLogger(ChannelOrderService.class);

    @Resource(name="msConfigService")
    private ConfigService configService;

	@Resource(name = "webDaoSupport")
	private BaseDaoSupport dao;

    @Autowired
    private com.jhl.service.ChannelOrderService webDao;

    @Autowired
    JytProxy jytProxy ;

    ExecutorService threadExecutor = Executors.newFixedThreadPool(5);

    /*
    * 新增
    */
	public void save(PageData pd)throws Exception{
		dao.save("ChannelOrderMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ChannelOrderMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ChannelOrderMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ChannelOrderMapper.datalistPage", page);
	}

	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ChannelOrderMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ChannelOrderMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ChannelOrderMapper.deleteAll", ArrayDATA_IDS);
	}

/*    *//**
     * 查询总交易次数和金额
     *//*
    public PageData selectAllChannelResult(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ChannelOrderMapper.selectAllChannelResult", pd);
    }

    *//**
     * 查询成功交易次数和金额
     *//*
    public PageData selectSuccChannelResult(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ChannelOrderMapper.selectAllChannelResult", pd);
    }

    *//**
     * 查询失败交易次数和金额
     *//*
    public PageData selectFailChannelResult(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ChannelOrderMapper.selectFailChannelResult", pd);
    }*/

    /**
     * 查询各个交易状态的记录数和交易金额
     */
    public List<PageData> selectStatusChannelReault(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ChannelOrderMapper.selectStatusChannelReault", pd);
    }

    /**
     * 查询数组中的ID是否都是新建状态
     */
    public boolean findArrayIDisNew(String[] arrayDATA_ids, String status) throws Exception {
        boolean flag = false;
        for(String ids : arrayDATA_ids) {
            PageData pd = (PageData) dao.findForObject("ChannelOrderMapper.selectStatusIsIn", Integer.parseInt(ids));
            String count = pd.get("status").toString();
            if(count.equals(status)) {
                flag = true;
            } else {
                flag = false;
                break;
            }

        }

        return flag;
    }

    /**
     * 审批通过
     * @param arrayDATA_ids
     */
    public HashMap<String, List> passAll(String[] arrayDATA_ids, int status) throws Exception {
        Long cashLimit = 500000L;//50w default value

        //cashLimit = 5l; //for test
        try {
            cashLimit = Long.parseLong(configService.findValueByKey(ConfigKey.JYT_CASH_LIMIT));
        }catch (Exception e ){
            logger.error(SessionUtil.getNo() + "获取单日提现金额限制出错，使用默认50万，请配置jyt.cash.limit值",e);
        }
        cashLimit = cashLimit * 100; //单位转换为分
        List<String> successList = new LinkedList<>();
        List<String> errorList = new LinkedList<>();

        for(String id : arrayDATA_ids){
            PageData pd = new PageData();
            pd.put("id" , id );
            pd.put("type" , ChannelOrder.Type.CASH.getValue());
            pd.put("status_success" , ChannelOrder.STATUS_SUCCESS);
            pd.put("status_withdraw" , ChannelOrder.STATUS_TRANSACTION_SUCCESS);
            pd.put("status_agree" , ChannelOrder.STATUS_AGREE);

            Long amount =  (Long)dao.findForObject("ChannelOrderMapper.selectCashTotal", pd);
            if(amount == null ) amount = 0L ;

            if(amount < cashLimit){
                //change status
                if(agreeCashApply(id , cashLimit - amount , status))
                    successList.add(id);
                else
                    errorList.add(id);
            }else{
                logger.warn("超过提现最大金额，不允许提现id:{}",id);
                //record the error id
                errorList.add(id);
            }
        }

        HashMap<String,List> rs = new HashMap<>();
        rs.put("success" , successList) ;
        rs.put("error" , errorList) ;
        return rs;
    }

    /**
     * 汇总
     */
    public PageData getAmountAll(String[] ArrayDATA_IDS) throws Exception {
       return (PageData) dao.findForObject("ChannelOrderMapper.sumAll", ArrayDATA_IDS);
    }

    /**
     * 同意单个提现请求
     * @param id
     */
    private boolean agreeCashApply(String id , long limit  , int status)  {
        PageData pd = new PageData();
        pd.put("ID" , id ) ;
        pd.put("STATUS" , status);
        pd.put("limit" , limit ) ;
        try {
            boolean rs =  0 < (int)dao.update("ChannelOrderMapper.agreeCashApply" , pd);
            callJytCashAPI( id );
            return rs;
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "同意提现申请失败", e);
        }
        return false;
    }

    /**
     * 现成调用金运通提现接口
     * @param id
     */
    private void callJytCashAPI(String id ){
        threadExecutor.execute( new Runnable() {
            @Override
            public void run() {
                try {

                    ChannelOrder order = webDao.queryById(Integer.parseInt(id) );

                    ChannelOrder cash = jytProxy.cash(order);
                    webDao.update(cash);

                }catch (Exception e ){
                    logger.error(SessionUtil.getNo() + "提现接口调用失败",e);
                }
            }
        });
    }

    /**
     * 修改其状态
     */
    public void updateStatus(String[] arrayDATA_IDS) throws Exception {
        PageData pd =  new PageData();
        pd.put("STATUS", ChannelOrder.STATUS_AGREE);
        for(int i = 0 ; i < arrayDATA_IDS.length; i++) {
            pd.put("ID",arrayDATA_IDS[i]);
            dao.update("ChannelOrderMapper.updateStatus", pd);
        }

    }
}

