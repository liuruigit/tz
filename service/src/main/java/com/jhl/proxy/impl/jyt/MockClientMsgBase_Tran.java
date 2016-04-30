package com.jhl.proxy.impl.jyt;

import com.jhl.proxy.impl.jyt.util.*;
import com.jhl.exception.AppException;
import com.jhl.util.HttpClient431Util;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Assert;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;




public class MockClientMsgBase_Tran {
	
	protected static final Logger log = Logger.getLogger(MockClientMsgBase_Tran.class);
	//测试环境    外部访问地址    						http://test1.jytpay.com:20080/JytAuth/tranCenter/authReq.do
	public static String APP_SERVER_URL = "http://10.10.10.103:8080/JytCPService/tranCenter/encXmlReq.do";
	
	//测试环境测试商户
	public static String MERCHANT_ID = "290041120001";	//替换为自己的商户号
	
	public static String RESP_MSG_PARAM_SEPARATOR = "&";
	
	/**返回报文merchant_id字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_MERCHANT_ID = "merchant_id=";
	
	/**返回报文xml_enc字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_XML_ENC = "xml_enc=";
	/**返回报文xml_enc字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_KEY_ENC = "key_enc=";
	
	/**返回报文sign字段前缀*/
	public static String RESP_MSG_PARAM_PREFIX_SIGN = "sign=";
	
	//替换为自己的客户端私钥
	//测试环境
	public static String CLIENT_PRIVATE_KEY="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDIAuGmpyZPfS9Y"+
			"XzhTmZkExMcnvMM0hzpRxxvpxAC2YooXECpPcI4HTROuH2OckLosFWzzc8ud7NCO"+
			"iQ3ZEjNexoH7+0Gpqiu/ygRUiWNstwb7wAYoZHsVFzcA/HXcNAIrjaKHQKbQdYJe"+
			"g8k0XzI8SKJd0t6UWgtc48QERxrpDf+nAGQtZuPCuB/YXKzIZ/eF8tuAkkGg16zH"+
			"bJVLWYkWKq/tjOv5UK+BiroN7osVzO2LYNj6jcFKxRaxmGqBCaOydcUJMQ4ZBx/K"+
			"qE0TKHR08TY4lmS1TSu5uitFfSG+/WKI7w/Iicq/1V4VFdXnL5ezJK8+hAD1hzJG"+
			"H3Px4tk/AgMBAAECggEARfbkElTUDOhAgIxGes/2+/kNpXPdYOmQ1/xrYjmC+km7"+
			"/ldURZecxycKJFWslZ720ObpxruR1Xt6dPGyW2WHfLa5z3hTvtLdHOjA3QV2NxhL"+
			"JBNG1ChZ+cW2Iu0tGUtEGxIfcWqHL9J7OS6mWmCWhQnvahySaZiZ8vNlpQ1ud2kj"+
			"umAbhCrgxG8LAATsNOm9wamNM3R5PEnKvJlcsWXoS4VkanpVRbDLLXhL6DTsjJKe"+
			"N8gpjfteTO/gGrc4OQJMuabSVcTOb+M0cz776HhG0Jem5ESZ95zFKtT5hNLuKOsZ"+
			"Z8T/TQp/5dkZ2oDBJr8kLsL6eBP0/t1IwOF+G2jcQQKBgQD/IYyeT5fhRTxhcalo"+
			"U4K5duaRoKdU0bKw5LJAQQBVsLZYznOWBQ6t093hmoZTD5LTHC4yxsTdwVfJvnKz"+
			"7M/l+NYKR2oHFJRzmd9r9DXFdgz27vn/9PApvUlUDTybhMuJ+2KktKGiKONyLlw+"+
			"YPYmGjM0btciAMKoH+Fc6CC/YQKBgQDIsUXVbhGZLu09HzMly1awq7oDREFktdQz"+
			"4g6G5zBBX1ao14iPc7RP1+oAi5uIgmlf90L7iaidDFs1t1WE6AQIExNRVdZNJgq0"+
			"mvckVD7LK8yoTS3FqoYdigYPM46Urc1c7Rwig+ELw5zHEPZdoTj5u8sQLalxHvms"+
			"/9CN9Hl8nwKBgQCiQOTb/YR62HxZAF8boRie+JYUAVbJo37/7sHKCUy8MmEVQ17R"+
			"BrBaLkaK5qoMgvf+WIqs8ipbWU2KWRs7gByLheAhs18j15u9lRs43Zug073VARBv"+
			"Dof6E45h7PUhdqe3Pe9zDglJRrW3dPJnHWUNwFXWP7ODxXYGVBgP7PR+oQKBgCi5"+
			"qHUmD9Yo6qVzllp+B8p0HpwZFrgMGdKpdc80TNuHMDu0+RY7IYTuSKLxsY8i/6Uk"+
			"tOf8SEYOQtgEqwr8Vg0ZadsgcwtWw+dgZvRtUAzEh5RDjR1GfE60OoSNLsrG9hx8"+
			"VTA/2MQdXswaihP36mrrC8rtqSu2YG4Vv0l/KcP7AoGBALOglf4HqRoK2uzKpIYk"+
			"c0AuJ25GJ5g/Z+9hjNrVcknH9CUHh4wWgoV6p0aSlBb2KID1UOXctHkqxlS9py41"+
			"Tl0tMGw1EBJvKSmzmlhyHFkq2kcsbGkZJ4CbFfAygA359QanBg0yknFL+I34sQVN"+
			"QfH28LW7PeB4sPGlLfh+TBgR";
	
	//替换匹配的服务端公钥
	//测试环境
	public static String SERVER_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz9rX1JG51udh5L6Co1od"+
			"tH/ELJ0t/0aUPp/yiT8hMcNHWdD4H6fTbSLyrWdT8nlkkKuHCEPBcY9eXwcw7P3p"+
			"pEdrqE105IZSJjpfiAwlQ1TRsDpJ0sDx8OEOnu2NQAlKvuQCFe9zS29iHoUB/zSN"+
			"Ohi9mvy0Z+/5lu/nKGg/RrCbsC+hMAHwgMS0pzLOCxZzuKy8TwkL9PuUVD2MiYfH"+
			"QfzoO3EcYvaMjjbLgPSEkP6iqeudooEHmWYeQAeSuq5KWoM75R4/dPHMa9oByETz"+
			"fIDK3dETjc2K5S5jDQLopoPB6oNy2Esfh3sY1CMbqLMdk66FdfGNGgQoCv4MnK6W"+
			"UQIDAQAB";
	
	
	public RSAHelper rsaHelper = new RSAHelper();
	{
		try {
			rsaHelper.initKey(CLIENT_PRIVATE_KEY, SERVER_PUBLIC_KEY, 2048);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取交易流水号
	 * <p> Create Date: 2014-5-10 </p>
	 * @return
	 */
	public String getTranFlow(){
		return MERCHANT_ID+RandomStringUtils.randomNumeric(18);
	}

	/**
	 * 获得报文头字符串
	 * <p> Create Date: 2014-5-10 </p>
	 * @param tranCode
	 * @return
	 */
	public String getMsgHeadXml(String tranCode,String tranFlow){
		StringBuffer headXml = new StringBuffer();
		headXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><message><head><version>1.0.0</version>");
		headXml.append("<tran_type>01</tran_type>")
				.append("<merchant_id>").append(MERCHANT_ID).append("</merchant_id>");
		headXml.append("<tran_date>").append(DateTimeUtils.getNowDateStr(DateTimeUtils.DATE_FORMAT_YYYYMMDD))
				.append("</tran_date>");
		headXml.append("<tran_time>").append(DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_HHMMSS))
				.append("</tran_time><tran_flowid>").append(tranFlow)
				.append("</tran_flowid><tran_code>").append(tranCode).append("</tran_code>");
		headXml.append("</head>");

		return headXml.toString();
	}

	public String sendMsg(String xml,String sign) throws Exception{
		log.info("上送报文："+xml);
		log.info("上送签名："+sign);
		
		byte[] des_key = DESHelper.generateDesKey() ;
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("merchant_id", MERCHANT_ID);
		paramMap.put("xml_enc", encryptXml(xml,des_key));
		paramMap.put("key_enc", encryptKey(des_key));
		paramMap.put("sign", sign);
	
		// 获取执行结果

		String res = HttpClient431Util.doPost(paramMap, APP_SERVER_URL);
		
		if(res  == null){
			log.error("服务器连接失败");
			
			throw new AppException("测试异常");
		}else{
			log.info("连接服务器成功,返回结果"+res);
		}
		String []respMsg = res.split(RESP_MSG_PARAM_SEPARATOR);
		
		String merchantId = respMsg[0].substring(RESP_MSG_PARAM_PREFIX_MERCHANT_ID.length());
		String respXmlEnc = respMsg[1].substring(RESP_MSG_PARAM_PREFIX_XML_ENC.length());
		String respKeyEnc = respMsg[2].substring(RESP_MSG_PARAM_PREFIX_KEY_ENC.length());
		String respSign = respMsg[3].substring(RESP_MSG_PARAM_PREFIX_SIGN.length());
		
		byte respKey[] = decryptKey(respKeyEnc) ;
		
		String respXml = decrytXml( respXmlEnc,respKey ) ;
		
		
		log.info("返回报文merchantId:"+merchantId);
		log.info("返回报文XML:"+respXml);
		log.info("返回报文签名:"+respSign);

		Assert.isTrue(verifyMsgSign(respXml, respSign),"返回报文校验失败");
		
		return respXml;
	}
	
	public String getMsgRespCode(String respMsg) throws Exception{
        SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));
		
		//解析报文头
		Node packageHead = doc.selectSingleNode("/message/head");
		
		Node respCodeNode = packageHead.selectSingleNode("resp_code");
		
		return respCodeNode.getText();
	}
	
	public String getMsgState(String respMsg) throws Exception{
        SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new InputSource(new StringReader(respMsg)));
		
		//解析报文头
		Node packageHead = doc.selectSingleNode("/message/body");
		if(packageHead==null)
			return null;
		
		Node tranStateNode = packageHead.selectSingleNode("tranState");
		if(tranStateNode==null)
			return null;
		
		return tranStateNode.getText();
	}
	
	
	private String encryptKey( byte[] key){
		
		byte[] enc_key = null ;
		try {
			enc_key = rsaHelper.encryptRSA(key, false, "UTF-8") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return StringUtil.bytesToHexString(enc_key) ;
	}
	
	public byte[] decryptKey(String hexkey){
		byte[] key = null ;
		byte[] enc_key = StringUtil.hexStringToBytes(hexkey) ;
		
		try {
			key = rsaHelper.decryptRSA(enc_key, false, "UTF-8") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return key ;
	}
	
	private String encryptXml( String xml, byte[] key){
		
		String enc_xml = CryptoUtils.desEncryptToHex(xml, key) ;
		
		return enc_xml;
	}
	
	public String decrytXml(String xml_enc, byte[] key) {
		String xml = CryptoUtils.desDecryptFromHex(xml_enc, key) ;
		return xml;
	}

	public boolean verifyMsgSign(String xml, String sign)
	{
		byte[] bsign = StringUtil.hexStringToBytes(sign) ;
		
		boolean ret = false ;
		try {
			ret = rsaHelper.verifyRSA(xml.getBytes("UTF-8"), bsign, false, "UTF-8") ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	public String signMsg( String xml ){
		String hexSign = null ;
		
		try {
			byte[] sign = rsaHelper.signRSA(xml.getBytes("UTF-8"), false, "UTF-8") ;
			
			hexSign = StringUtil.bytesToHexString(sign) ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hexSign;
	}
	//验签
	public boolean verifySign(byte[] plainBytes, byte[] signBytes){
		boolean flag = false;
		try {
			flag = rsaHelper.verifyRSA(plainBytes, signBytes, false, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}
