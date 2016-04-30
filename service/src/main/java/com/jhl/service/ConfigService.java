package com.jhl.service;


import com.google.common.base.Strings;
import com.jhl.dao.ConfigDao;
import com.jhl.pojo.config.Config;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置 Service
 * @author v5inter
 * 2016 - 1 - 24
 */
@Service("configService")
public class ConfigService extends BaseService<Config> {
    
    final Logger logger = LoggerFactory.getLogger(getClass());
    
    // 缓存配置(非线程安全)
    private Map<String, String> cache = new HashMap<String, String>();
    
	@Resource(name = "configDao")
	private ConfigDao configDao;

    /**
     * 加载数据库配置
     */
	public void reload(){
	    try {
            List<Config> list = configDao.queryAllConfig();
            if(list != null) {
                Map<String, String> map = new HashMap<String, String>();
                for(Config entity : list) {
					String mapKey = entity.getKey();
					map.put(mapKey , entity.getValue());
				}
                cache = map;
            }
            logger.info("加载配置缓存成功");
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "加载配置异常！",e);
        }
	}

    /**
     * 获取配置
     * @param key
     * @return
     */
	public String getCacheConfigByKey(String key){
		if (Strings.isNullOrEmpty(key)) {
			return null;
		}
        if(cache.isEmpty())reload();
	    String val = cache.get(key);
        if (Strings.isNullOrEmpty(val)){
            logger.warn("尝试获取配置："+key,"失败，值为空！");
            return "";
        }else {
            return val;
        }
	}

    /**
     * 获取系统配置数量
     * @return
     */
    public int getConfigSize(){
        return cache.size();
    }

}
