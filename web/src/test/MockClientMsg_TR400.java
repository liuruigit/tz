import com.jhl.proxy.impl.jyt.MockClientMsgBase_Auth;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:spring-dispatcher.xml"})
public class MockClientMsg_TR400 extends MockClientMsgBase_Auth {

	protected static final Logger log = Logger.getLogger(MockClientMsg_TR400.class);
	
	public static String TRAN_CODE = "TR4003";//交易请请求码
	
	/**
	 * 实名认证四要素
	 * <p> Create Date: 2014-5-10 </p>
	 * @throws Exception 
	 */
	@Test
	public void testSC0001_T_D_Not_In_Repo() throws Exception{
		
		String bankCardNo = "6225887864565700";
		String bankCode="";
		String idNum = "510703198507010712";
		String idName = "吴海黎";
		String terminalType = "01";

		String bankCarkType = "A";//D   C
		
		String xml= getSc0001Xml( bankCardNo, bankCode, idNum, idName,  terminalType,bankCarkType,getTranFlow());
		System.out.println(xml);
		String mac=signMsg(xml);//报文加签
		
        String respXml = sendMsg(xml, mac);//向服务器发送数据
        String respCode = getMsgRespCode(respXml, "resp_code");//解析响应数据
        String bankName = getMsgState(respXml,"bank_name");//解析响应数据
        System.out.println("响应信息： "+respXml);
        System.out.println("返回响应码： "+ respCode);
        System.out.println("返回响应码： "+ bankName);
       // Assert.assertTrue("交易执行结果错误",XmlMsgConstant.MSG_RES_CODE_SUCCESS.equals(respCode));
	}
	/**
	 * 获得SC0001的上送报文字符串
	 * <p> Create Date: 2014-5-10 </p>
	 * @return
	 */
	public String getSc0001Xml(String bankCardNo,String bankCode,String idNum,String idName,
							   String terminalType,String bankCarkType,String tranFlow){
		StringBuffer xml = new StringBuffer();
		xml.append(getMsgHeadXml(TRAN_CODE,tranFlow));
		xml.append("<body><bank_card_no>").append(bankCardNo).append("</bank_card_no><bank_code>").append(bankCode).append("</bank_code>");
		xml.append("<id_num>").append(idNum).append("</id_num><id_name>").append(idName).append("</id_name>");
		xml.append("<terminal_type>").append(terminalType).append("</terminal_type>");
		xml.append("<bank_card_type>").append(bankCarkType).append("</bank_card_type>");
		xml.append("<phone_no>").append("18929353864").append("</phone_no>");
		xml.append("</body></message>");
		return xml.toString();
	}
}
