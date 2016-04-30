package com.jhl.common.listener;

import com.jhl.common.sms.BaseSmsConnector;
import com.jhl.constant.ConfigKey;
import com.jhl.security.JwtHolder;
import com.jhl.service.BankCardService;
import com.jhl.service.ConfigService;
import com.jhl.service.SecurityQuestionService;
import com.jhl.service.SysConfig;
import com.jhl.util.DESUtil;
import com.jhl.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web容器启动
 */
public class ApplicationInitListener implements ServletContextListener {

	ApplicationContext app;
	private static Logger logger = Logger.getLogger(ApplicationInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("===========Listener start.===========");
		try {
			app = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
			ConfigService configService = (ConfigService)app.getBean("configService");
			BankCardService bankCardService = (BankCardService)app.getBean("bankCardService");
			SecurityQuestionService securityQuestionService = (SecurityQuestionService)app.getBean("webSecurityQuestionService");
			configService.reload();
			bankCardService.initBankRule();
			securityQuestionService.initSec();
			DESUtil.setKey(SysConfig.geteConfigByKey(ConfigKey.SECRET_KEY));//设置数据加密秘钥
			BaseSmsConnector.init();
			JwtHolder._instance().initSecret();
		} catch (Exception e) {
			logger.error(SessionUtil.getNo() + "系统初始化失败！",e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("===========Listener stop.===========");
	}

}
