package com.jhl.service.biz;

import com.jhl.constant.ConfigKey;
import com.jhl.dao.BaseDaoSupport;
import com.jhl.entity.Page;
import com.jhl.service.SysConfig;
import com.jhl.util.DESUtil;
import com.jhl.util.PageData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("msConfigService")
public class ConfigService implements InitializingBean{

	@Resource(name = "webDaoSupport")
	private BaseDaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ConfigMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ConfigMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ConfigMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ConfigMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ConfigMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ConfigMapper.findById", pd);
	}

	/*
	* 通过KEY获取数据
	*/
	public String findValueByKey(String key)throws Exception{
		PageData result = (PageData)dao.findForObject("ConfigMapper.findByKey", key);
		return result.getString("VALUE");
	}

	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ConfigMapper.deleteAll", ArrayDATA_IDS);
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        DESUtil.setKey(findValueByKey(ConfigKey.SECRET_KEY));
    }
}

