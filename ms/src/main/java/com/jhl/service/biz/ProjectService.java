package com.jhl.service.biz;

import com.jhl.dao.BaseDaoSupport;
import com.jhl.entity.Page;
import com.jhl.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service("msProjectService")
public class ProjectService {

	@Resource(name = "webDaoSupport")
	private BaseDaoSupport dao;
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);


	/**
	 * 每三分鐘檢查一次
	 */
/*	@Scheduled(cron = "0 0/3 * * * ?")
	public void periodOpenPro(){
		try {
			Long now = System.currentTimeMillis();
			List<PageData> pageDatas = selectNew(new Page());
			for (PageData pageData : pageDatas) {
				Long openDate = DateUtil.pasreStringToLong(pageData.getString("OPEN_DATE"),"YYYY-MM-dd hh:mm:ss");
				if (now >= openDate || Math.abs(now - openDate) < 60 * 1000){//當前時間大於開放購買時間，或她們的差值在1分鐘以內，都開發
					dao.update("ProjectMapper.updateToOpen", pageData);
				}
			}
		} catch (Exception e) {
			logger.error(SessionUtil.getNo() + "定時任務執行異常",e);
		}
	}*/

	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		pd.remove("ID");
		PageData checkResult = saveCheck(pd);
		if (checkResult != null){
			dao.save("ProjectMapper.save", pd);
		}else {
			throw new IllegalArgumentException("保存参数错误！");
		}
	}

	private PageData saveCheck(PageData pd) throws Exception {
		for (Object key : pd.keySet()) {
			if (StringUtils.isEmpty(pd.get(key))) throw new IllegalArgumentException("参数:" + key.toString()+"值为空！");
		}
		PageData formatPd = dataFormat(pd);
		boolean checkResult;
		checkResult = formatPd.get("NO").toString().length() <= 30;
		return checkResult ? formatPd : null;
	}

	private PageData dataFormat(PageData pd) throws Exception{
		Double min = Double.parseDouble(pd.getString("MIN"));
		Double step = Double.parseDouble(pd.getString("STEP"));
		Double limit = Double.parseDouble(pd.getString("LIMIT"));
		Double amount = Double.parseDouble(pd.getString("AMOUNT"));
//		Double serviceRate = Double.parseDouble(pd.getString("SERVICE_RATE")) / 100;
//		Double serviceRate = Double.parseDouble(pd.getString("SERVICE_RATE")) / 100;

		pd.put("MIN",min);
		pd.put("STEP",step);
		pd.put("LIMIT",limit);
		pd.put("AMOUNT",amount);
//		pd.put("SERVICE_RATE",serviceRate);
		pd.put("SELLED_AMOUNT",0);
		return pd;
	}


	/**
	 * 修改标的状态
	 * @param pd
	 * @throws Exception
     */
	public void updateStatus(PageData pd) throws Exception {
		dao.update("ProjectMapper.updateStatus", pd);
	}

	/**
	 * 标的删除，修改逻辑删除位
	 * @param pd
	 * @throws Exception
     */
	public void updateDeleteStatus(PageData pd) throws Exception {
		dao.update("ProjectMapper.updateDeleteStatus", pd);
	}

	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ProjectMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ProjectMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.datalistPage", page);
	}

	public List<PageData> selectNew(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.selectNew", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProjectMapper.findById", pd);
	}

	public PageData findByNo(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProjectMapper.findByNo", pd);
	}

	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.update("ProjectMapper.deleteAll", ArrayDATA_IDS);
	}

}

