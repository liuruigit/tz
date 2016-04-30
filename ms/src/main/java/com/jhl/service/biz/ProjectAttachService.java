package com.jhl.service.biz;

import com.jhl.dao.BaseDaoSupport;
import com.jhl.entity.Page;
import com.jhl.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("projectAttachService")
public class ProjectAttachService {

	@Resource(name = "webDaoSupport")
	private BaseDaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
			dao.save("ProjectAttachMapper.save", pd);
	}

	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ProjectAttachMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ProjectAttachMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectAttachMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectAttachMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProjectAttachMapper.findById", pd);
	}

	public PageData findByNo(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProjectAttachMapper.findByNo", pd);
	}

	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProjectAttachMapper.deleteAll", ArrayDATA_IDS);
	}

	public int checkSaveFile(PageData pd) throws Exception {
		return (int)dao.findForObject("ProjectAttachMapper.findFileByID",pd);
	}

	/*
	* 保存URL
	 */
	public void saveURL(PageData pd) throws Exception {
		dao.update("ProjectAttachMapper.saveURL", pd);
	}

}

