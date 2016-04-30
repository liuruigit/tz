package com.jhl.listener;

import com.jhl.common.sms.BaseSmsConnector;
import com.jhl.constant.ConfigKey;
import com.jhl.service.ConfigService;
import com.jhl.service.SysConfig;
import com.jhl.util.Const;
import com.jhl.util.DESUtil;
import com.jhl.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
* 类名称：WebAppContextListener.java
* 类描述： 
* 作者：FH 
* 联系方式：
* @version 1.0
 */
public class WebAppContextListener implements ServletContextListener {

	ApplicationContext app;
	private static Logger logger = Logger.getLogger(WebAppContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("===========Listener start.===========");
		try {
			Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());

			ConfigService configService = (ConfigService)app.getBean("configService");
			configService.reload();
			DESUtil.setKey(SysConfig.geteConfigByKey(ConfigKey.SECRET_KEY));//设置数据加密秘钥
			BaseSmsConnector.init();
		} catch (Exception e) {
			logger.error(SessionUtil.getNo() + "系统初始化失败！",e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}
