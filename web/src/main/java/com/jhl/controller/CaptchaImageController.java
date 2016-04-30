package com.jhl.controller;

import com.jhl.dto.BaseDto;
import com.jhl.dto.QrDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.json.JsonBack;
import com.jhl.proxy.impl.jyt.util.StringUtil;
import com.jhl.util.CaptchaCode;
import com.jhl.util.QRCodeUtil;
import com.jhl.util.SeqNoUtil;
import com.jhl.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Account: xin.fang
 * Date: 14-06-04
 * Time: 上午 10:30
 * 生成验证码图片的controller
 */
@Controller
@RequestMapping(value = "captcha")
public class CaptchaImageController extends BaseController {

	private Logger logger = Logger.getLogger(CaptchaImageController.class);

	@RequestMapping("refresh")
	public @ResponseBody JsonBack captchaImage() {
		ByteArrayOutputStream out = null;
		String no = SeqNoUtil.nextSeqNo();
		JsonBack jsonBack = new JsonBack();
		try {
			CaptchaCode captchaCode = new CaptchaCode();
			BufferedImage image = captchaCode.creatImage();
			// store the text in the session
			redisClient.expireSet(no,captchaCode.getSRand(),300);//验证码60s过期，// TODO: 2016/2/18 阿里云redis策略
			System.out.println(captchaCode.getSRand());
			Base64.Encoder encoder = Base64.getEncoder();
			out = new ByteArrayOutputStream();
			ImageIO.write(image,"jpg",out);
			String img = encoder.encodeToString(out.toByteArray());
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("image",img);
			result.put("no",no);
			return buildSuccJsonBack(jsonBack,result);
		} catch (Exception e) {
			logger.error(SessionUtil.getNo() + "获取验证码图片出现异常!", e);
		} finally {
			try {
				if(out != null)out.close();
			} catch (IOException e) {
				logger.error(SessionUtil.getNo() + "获取验证码图片时关闭流出现异常!", e);
			}
		}
		return buildErrorJsonBack(jsonBack);
	}


	@RequestMapping(value = "/verify")
	public @ResponseBody
	JsonBack captchaImage(BaseDto dto) {
		JsonBack jsonback = new JsonBack();
		String val = redisClient.getStr(dto.getCaptchaKey());
		if (StringUtils.isEmpty(val)) {
			return buildErrorJsonBack(jsonback,"验证码已过期");
		}else {
//			System.out.println("val:"+val+",dtoVal:"+dto.getCaptchaVal() + ",result:"+StringUtil.equalsIgnoreCase(dto.getCaptchaVal(),val));
			if (StringUtil.equalsIgnoreCase(dto.getCaptchaVal(),val)){
				return buildSuccJsonBack(jsonback);
			}else{
				return buildErrorJsonBack(jsonback,"验证码错误");
			}
		}
	}

	/**
	 * 生成用户二维码
	 * @param response
	 * @throws IOException
     */
	@RequestMapping("auth/qrCode")
	public void qrCode(HttpServletResponse response, QrDto dto) {
		Account account = SessionUtil.getSession();
		String url = "https://www.jinhulu.com/register.html?recommendId=" + account.getId();
		setResponseHeaders(response);
		try {
			ImageIO.write(QRCodeUtil.buildQRCode(url,dto.getWidth(),dto.getHeight()), "png", response.getOutputStream());
		} catch (Exception e) {
			logger.error("生成用户二维码异常",e);
		}
	}

	protected void setResponseHeaders(HttpServletResponse response) {
		response.setContentType("image/png");
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		long time = System.currentTimeMillis();
		response.setDateHeader("Last-Modified", time);
		response.setDateHeader("Date", time);
		response.setDateHeader("Expires", time);
	}
}
