package com.jhl.service.biz;


import com.google.common.base.Strings;
import com.jhl.dao.BaseDaoSupport;
import com.jhl.entity.Page;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.BankCard;
import com.jhl.service.AccountService;
import com.jhl.service.BankCardService;
import com.jhl.service.MessageService;
import com.jhl.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service("unbindingapplyService")
public class UnBindingApplyService {

	@Resource(name = "webDaoSupport")
	private BaseDaoSupport dao;

    @Resource(name = "messageService")
	MessageService messageService;
	@Resource(name = "bankCardService")
	BankCardService bankCardService;
	@Resource(name = "accountService")
	AccountService accountService;
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("UnBindingApplyMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("UnBindingApplyMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("UnBindingApplyMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UnBindingApplyMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UnBindingApplyMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UnBindingApplyMapper.findById", pd);
	}

    /*
    * 通过id获取数据银行卡相关信息
    */
    public PageData findWithBankInfo(PageData pd)throws Exception{
        return (PageData)dao.findForObject("UnBindingApplyMapper.findWithBankInfo", pd);
    }


    /*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UnBindingApplyMapper.deleteAll", ArrayDATA_IDS);
	}

    /**
     * 批量审批通过
     * @param arrayDATA_ids
     */
    public void passAll(String[] arrayDATA_ids) {

    }

	public boolean disagree(PageData pd) throws Exception {
		pd.put("STATUS",1);
		pd.put("UPDATE_TIME" , new Date());
		boolean rs = (int) dao.update("UnBindingApplyMapper.edit", pd) > 0;

		if(rs ) {
			//send sms message
			String bankcardId = (String)pd.get("bankcard_id");
			String title = (String)pd.get("msgTitle");
			String content = (String)pd.get("msgContent");
			String accId = (String) pd.get("ACC_ID");

			if (!Strings.isNullOrEmpty(accId)){
				Account account = accountService.queryById(Integer.parseInt(accId));
				BankCard bankCard = bankCardService.queryById(Integer.parseInt(bankcardId));
				if (account!=null) {
					messageService.asySendSmsAndNotice(title,content,account);
				}
			}
		}
		return rs;
	}

    public boolean updateAgree(PageData pd) throws Exception {

        pd.put("STATUS", Account.PrepareInvestStep.TRADE_PWD.getValue());
        pd.put("UPDATE_TIME" , new Date());
        boolean rs = (int) dao.update("UnBindingApplyMapper.edit", pd) > 0;

        if(rs ) {
            //send sms message
            String bankcardId = (String)pd.get("bankcard_id");
            String title = (String)pd.get("msgTitle");
            String content = (String)pd.get("msgContent");
            String accId = (String) pd.get("ACC_ID");

			if (!Strings.isNullOrEmpty(accId) && !Strings.isNullOrEmpty(bankcardId)){
				Account account = accountService.queryById(Integer.parseInt(accId));
				BankCard bankCard = bankCardService.queryById(Integer.parseInt(bankcardId));
				if (account!=null && bankCard!=null) {
					bankCard.setIsDefault(1);
					bankCard.setDeleteState(0);
					account.setPrepareStep(Account.PrepareInvestStep.TRADE_PWD.getValue());
					accountService.updatePrepareStep(account);
					accountService.updateSession(account);
					bankCardService.unbinding(bankCard);
					messageService.asySendSmsAndNotice(title,content,account);
				}
			}
        }

        return rs;
    }
}

