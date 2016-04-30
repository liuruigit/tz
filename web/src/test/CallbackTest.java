import com.jhl.pojo.biz.Account;
import com.jhl.proxy.impl.jyt.JytCallback;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/3/27.
 */
public class CallbackTest extends BaseTest {

    @Autowired
    JytCallback jytCallback;

    @Test
    public void testAccount() throws Exception {
        jytCallback.jhTransaction("<?xml version=\"1.0\" encoding=\"GB18030\"?>" +
                "<TX>" +
                "  <REQUEST_SN>2016022916560100000</REQUEST_SN>" +
                "  <CUST_ID>SZP717856009#002</CUST_ID>" +
                "  <TX_CODE>6WY101</TX_CODE>" +
                "  <RETURN_CODE>000000</RETURN_CODE>" +
                "  <RETURN_MSG></RETURN_MSG>" +
                "  <LANGUAGES>CN</LANGUAGES>" +
                "  <TX_INFO> " +
                " <ACCNO1>44201501100052511215</ACCNO1>" +
                " <CURR_COD>01</CURR_COD>" +
                " <ACC_NAME>公司零二</ACC_NAME>" +
                " <ACC_ORGAN>442000034</ACC_ORGAN>" +
                " <ACC_STATE>正常</ACC_STATE>" +
                " <INTR></INTR>" +
                " <TOTAL_PAGE>1</TOTAL_PAGE>" +
                " <PAGE>1</PAGE>" +
                " <POSTSTR>1010015131456736417027990+11+1+11+</POSTSTR>" +
                " <FLAG>0</FLAG>" +
                "   <FILE_LOCSTR>1010015131456736417027990</FILE_LOCSTR>" +
                "   <DETAILLIST>" +
                "" +
                "<DETAILINFO>" +
                "    <TRANDATE>2015/10/19</TRANDATE>" +
                "<TRANTIME>17:48:15</TRANTIME>   " +
                "<CRE_TYP>单位人民币结算卡业务凭证</CRE_TYP>   " +
                "<CRE_NO>9553307200000025</CRE_NO>     " +
                "<MESSAGE>劳务费</MESSAGE>   " +
                "<AMT>10.00</AMT>             " +
                "<AMT1>70.00</AMT1>         " +
                "<FLAG1>0</FLAG1>        " +
                "<ACCNO2>6214831217027006</ACCNO2>     " +
                "<ACC_NAME1>青高</ACC_NAME1>" +
                "<FLAG2>0</FLAG2>        " +
                "<TRAN_FLOW>442000034A890000083</TRAN_FLOW>" +
                "<BFLOW></BFLOW>       " +
                "<DET_NO>3465</DET_NO>     " +
                "        <DET></DET>" +
                "        <REAL_TRANDATE>2015/10/19</REAL_TRANDATE>   " +
                "</DETAILINFO>" +
                "<DETAILINFO>" +
                "    <TRANDATE>2015/10/19</TRANDATE>" +
                "<TRANTIME>17:48:46</TRANTIME>   " +
                "<CRE_TYP>单位人民币结算卡业务凭证</CRE_TYP>   " +
                "<CRE_NO>9553307200000025</CRE_NO>     " +
                "<MESSAGE>劳务费</MESSAGE>   " +
                "<AMT>10.00</AMT>             " +
                "<AMT1>60.00</AMT1>         " +
                "<FLAG1>0</FLAG1>        " +
                "<ACCNO2>6227007200601995105</ACCNO2>     " +
                "<ACC_NAME1>青高</ACC_NAME1>" +
                "<FLAG2>0</FLAG2>        " +
                "<TRAN_FLOW>442000034A890000084</TRAN_FLOW>" +
                "<BFLOW></BFLOW>       " +
                "<DET_NO>3466</DET_NO>     " +
                "        <DET></DET>" +
                "        <REAL_TRANDATE>2015/10/19</REAL_TRANDATE>   " +
                "</DETAILINFO>" +
                "<DETAILINFO>" +
                "    <TRANDATE>2015/10/19</TRANDATE>" +
                "<TRANTIME>17:49:14</TRANTIME>   " +
                "<CRE_TYP>单位人民币结算卡业务凭证</CRE_TYP>   " +
                "<CRE_NO>9553307200000025</CRE_NO>     " +
                "<MESSAGE>劳务费</MESSAGE>   " +
                "<AMT>10.00</AMT>             " +
                "<AMT1>50.00</AMT1>         " +
                "<FLAG1>0</FLAG1>        " +
                "<ACCNO2>6227007200601995105</ACCNO2>     " +
                "<ACC_NAME1>青高</ACC_NAME1>" +
                "<FLAG2>0</FLAG2>        " +
                "<TRAN_FLOW>442000034A890000085</TRAN_FLOW>" +
                "<BFLOW></BFLOW>       " +
                "<DET_NO>3467</DET_NO>     " +
                "        <DET></DET>" +
                "        <REAL_TRANDATE>2015/10/19</REAL_TRANDATE>   " +
                "</DETAILINFO>" +
                "<DETAILINFO>" +
                "    <TRANDATE>2015/10/19</TRANDATE>" +
                "<TRANTIME>17:49:47</TRANTIME>   " +
                "<CRE_TYP>单位人民币结算卡业务凭证</CRE_TYP>   " +
                "<CRE_NO>9553307200000025</CRE_NO>     " +
                "<MESSAGE>劳务费</MESSAGE>   " +
                "<AMT>10.00</AMT>             " +
                "<AMT1>40.00</AMT1>         " +
                "<FLAG1>0</FLAG1>        " +
                "<ACCNO2>6227007200601995105</ACCNO2>     " +
                "<ACC_NAME1>青高</ACC_NAME1>" +
                "<FLAG2>0</FLAG2>        " +
                "<TRAN_FLOW>442000034A890000086</TRAN_FLOW>" +
                "<BFLOW></BFLOW>       " +
                "<DET_NO>3468</DET_NO>     " +
                "        <DET></DET>" +
                "        <REAL_TRANDATE>2015/10/19</REAL_TRANDATE>   " +
                "</DETAILINFO>" +
                "<DETAILINFO>" +
                "    <TRANDATE>2015/10/19</TRANDATE>" +
                "<TRANTIME>17:50:32</TRANTIME>   " +
                "<CRE_TYP>单位人民币结算卡业务凭证</CRE_TYP>   " +
                "<CRE_NO>9553307200000025</CRE_NO>     " +
                "<MESSAGE>劳务费</MESSAGE>   " +
                "<AMT>10.00</AMT>             " +
                "<AMT1>30.00</AMT1>         " +
                "<FLAG1>0</FLAG1>        " +
                "<ACCNO2>6227007200601995105</ACCNO2>     " +
                "<ACC_NAME1>青高</ACC_NAME1>" +
                "<FLAG2>0</FLAG2>        " +
                "<TRAN_FLOW>442000034A890000087</TRAN_FLOW>" +
                "<BFLOW></BFLOW>       " +
                "<DET_NO>3469</DET_NO>     " +
                "        <DET></DET>" +
                "        <REAL_TRANDATE>2015/10/19</REAL_TRANDATE>   " +
                "</DETAILINFO>" +
                "<DETAILINFO>" +
                "    <TRANDATE>2015/10/19</TRANDATE>" +
                "<TRANTIME>17:51:00</TRANTIME>   " +
                "<CRE_TYP>单位人民币结算卡业务凭证</CRE_TYP>   " +
                "<CRE_NO>9553307200000025</CRE_NO>     " +
                "<MESSAGE>劳务费</MESSAGE>   " +
                "<AMT>10.00</AMT>             " +
                "<AMT1>20.00</AMT1>         " +
                "<FLAG1>0</FLAG1>        " +
                "<ACCNO2>6227007200601995105</ACCNO2>     " +
                "<ACC_NAME1>青高</ACC_NAME1>" +
                "<FLAG2>0</FLAG2>        " +
                "<TRAN_FLOW>442000034A890000088</TRAN_FLOW>" +
                "<BFLOW></BFLOW>       " +
                "<DET_NO>3470</DET_NO>     " +
                "        <DET></DET>" +
                "        <REAL_TRANDATE>2015/10/19</REAL_TRANDATE>   " +
                "</DETAILINFO>" +
                "<DETAILINFO>" +
                "    <TRANDATE>2015/10/19</TRANDATE>" +
                "<TRANTIME>17:51:29</TRANTIME>   " +
                "<CRE_TYP>单位人民币结算卡业务凭证</CRE_TYP>   " +
                "<CRE_NO>9553307200000025</CRE_NO>     " +
                "<MESSAGE>劳务费</MESSAGE>   " +
                "<AMT>10.00</AMT>             " +
                "<AMT1>10.00</AMT1>         " +
                "<FLAG1>0</FLAG1>        " +
                "<ACCNO2>6227007200601995105</ACCNO2>     " +
                "<ACC_NAME1>青高</ACC_NAME1>" +
                "<FLAG2>0</FLAG2>        " +
                "<TRAN_FLOW>442000034A890000089</TRAN_FLOW>" +
                "<BFLOW></BFLOW>       " +
                "<DET_NO>3471</DET_NO>     " +
                "        <DET></DET>" +
                "        <REAL_TRANDATE>2015/10/19</REAL_TRANDATE>   " +
                "</DETAILINFO>" +
                "<DETAILINFO>" +
                "    <TRANDATE>2015/10/19</TRANDATE>" +
                "<TRANTIME>17:52:46</TRANTIME>   " +
                "<CRE_TYP>单位人民币结算卡业务凭证</CRE_TYP>   " +
                "<CRE_NO>9553307200000025</CRE_NO>     " +
                "<MESSAGE>劳务费</MESSAGE>   " +
                "<AMT>10.00</AMT>             " +
                "<AMT1>0.00</AMT1>         " +
                "<FLAG1>0</FLAG1>        " +
                "<ACCNO2>6227007200601995105</ACCNO2>     " +
                "<ACC_NAME1>青高</ACC_NAME1>" +
                "<FLAG2>0</FLAG2>        " +
                "<TRAN_FLOW>442000034A890000090</TRAN_FLOW>" +
                "<BFLOW></BFLOW>       " +
                "<DET_NO>3472</DET_NO>     " +
                "        <DET></DET>" +
                "        <REAL_TRANDATE>2015/10/19</REAL_TRANDATE>   " +
                "</DETAILINFO>" +
                " </DETAILLIST>      " +
                "  </TX_INFO>" +
                "</TX>");

    }

}
