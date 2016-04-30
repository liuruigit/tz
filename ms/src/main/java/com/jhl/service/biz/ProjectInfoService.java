package com.jhl.service.biz;

import com.jhl.dao.BaseDaoSupport;
import com.jhl.entity.Page;
import com.jhl.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("projectinfoService")
public class ProjectInfoService {

	@Resource(name = "webDaoSupport")
	private BaseDaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ProjectInfoMapper.save", pd);
	}

	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ProjectInfoMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ProjectInfoMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectInfoMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectInfoMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProjectInfoMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.update("ProjectInfoMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	* 根据多个ID查询多个PROID
	 */
	public List<PageData> findProIdById(String[] ArrayDATA_IDS)throws Exception {
		return (List<PageData>)dao.findForList("ProjectInfoMapper.selectProIdById", ArrayDATA_IDS);
	}

	/**
	 * 根据pro_id 批量删除
	 * @param project_ID
	 * @return
	 * @throws Exception
     */
	public void deleteAllById(String[] ArrayDATA_IDS) throws Exception {
		dao.update("ProjectInfoMapper.deleteAllById", ArrayDATA_IDS);
	}

	/*
	 * 附件详情
	 */
	public List<PageData> findAttach(String project_ID)throws Exception {
		return (List<PageData>)dao.findForList("ProjectInfoMapper.selectDetils", project_ID);
	}

	/*
	 * 扫描是否存在融资标对应的信息
	 */
	public Integer findInfoByNo(PageData pd)throws Exception {
		return (Integer) dao.findForObject("ProjectInfoMapper.findInfoByNo", pd);
	}

	//根据标NO，逻辑删除资产信息
	public void updateStatus(PageData pd) throws Exception {
		dao.update("ProjectInfoMapper.updateStatus", pd);
	}

	/**
	 * 根据标的资产信息ID， 逻辑删除标的资产信息
	 */
	public void updateInfoStatus(PageData pd) throws Exception {
		dao.update("ProjectInfoMapper.updateInfoStatus", pd);
	}
}

