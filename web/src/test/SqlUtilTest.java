import com.jhl.dao.WechatDao;
import com.jhl.dto.InvestDto;
import com.jhl.pojo.biz.Account;
import com.jhl.service.AccountService;
import com.jhl.service.MessageService;
import com.jhl.service.WechatService;
import com.jhl.util.DESUtil;
import com.jhl.util.DateUtil;
import com.jhl.util.SessionUtil;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by hallywu on 16/1/30.
 */
public class SqlUtilTest extends BaseTest {
    @Autowired
    MessageService messageService;
    @Autowired
    AccountService accountService;
    @Autowired
    WechatDao wechatDao;


    /*@Test
    public void test(){
        String sql = "select * from A where col = 1";
        String count = "select count('x') " + sql.substring(sql.indexOf("from"));
        System.out.println(count);
    }*/

    /**
     * ***********************模拟微信发送模板消息***************************************************
     */

    /**
     * 投资申请通知测试
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        Account account = new Account();
        account.setAccName(DESUtil.getEncString("哈哈"));
        account.setRealName(DESUtil.getEncString("刘瑞"));
        account.setOpenId("oQ7YOwL0Xz3IQvOKRygWucUkJQQo");
        messageService.sendInvest("15337223683", "申请中", 1000, account);
    }

    /**
     * 提现申请通知测试
     */
    @Test
    public void test2() throws Exception {
        Account account = new Account();
        account.setAccName(DESUtil.getEncString("哈哈"));
        account.setRealName(DESUtil.getEncString("刘瑞"));
        account.setOpenId("oQ7YOwL0Xz3IQvOKRygWucUkJQQo");
        messageService.sendCashApply(1000, "申请中", account);
    }

    /**
     * 提现成功通知测试
     */
    @Test
    public void test3() throws Exception {
        Account account = new Account();
        account.setAccName(DESUtil.getEncString("哈哈"));
        account.setRealName(DESUtil.getEncString("刘瑞"));
        account.setOpenId("oQ7YOwL0Xz3IQvOKRygWucUkJQQo");
        messageService.sendCashSuccess(1000, account);
    }

    /**
     * 提现失败通知测试
     */
    @Test
    public void test4() throws Exception {
        Account account = new Account();
        account.setAccName(DESUtil.getEncString("哈哈"));
        account.setRealName(DESUtil.getEncString("刘瑞"));
        account.setOpenId("oQ7YOwL0Xz3IQvOKRygWucUkJQQo");
        messageService.sendFail(1000, "提现", account, null, null);
    }

    /**
     * 充值通知测试
     */
    @Test
    public void test5() throws Exception {
        Account account = new Account();
        account.setAccName(DESUtil.getEncString("哈哈"));
        account.setRealName(DESUtil.getEncString("刘瑞"));
        account.setOpenId("oQ7YOwL0Xz3IQvOKRygWucUkJQQo");
        messageService.sendRechargeNotice(1000, "正在充值", account);
    }

    /**
     * 充值成功通知测试
     */
    @Test
    public void test6() throws Exception {
        Account account = new Account();
        account.setAccName(DESUtil.getEncString("哈哈"));
        account.setRealName(DESUtil.getEncString("刘瑞"));
        account.setOpenId("oQ7YOwL0Xz3IQvOKRygWucUkJQQo");
        messageService.sendRechargeSuccess(1000, "金运通/银行转账", account);
    }

    /**
     * 充值成功通知测试
     */
    @Test
    public void test7() throws Exception {
        Account account = new Account();
        account.setAccName(DESUtil.getEncString("哈哈"));
        account.setRealName(DESUtil.getEncString("刘瑞"));
        account.setOpenId("oQ7YOwL0Xz3IQvOKRygWucUkJQQo");
        messageService.sendRechargeSuccess("1000元", "金运通/银行转账", account);
    }

    /**
     * ***************************模拟真实环境测试*************************************************
     */


    /**
     * 测试投资申请的消息发送功能
     * 所需参数：
     * Integer userId,
     * String accountName,
     * Integer projectId,
     * double amount,
     * String clientVersion,
     * String proName,
     * String couponId
     */

    @Test
    public void Test() throws Exception {


        Account session = wechatDao.getAccountByOpenID("oQ7YOwL0Xz3IQvOKRygWucUkJQQo");

        int projectId = 14;
        double amount = 5000;
        String clientVersion = "13";
        String proName = "平安大厦";
        String couponId = null;
        InvestDto dto = new InvestDto();
        dto.setVersion(clientVersion);
        dto.setProName(proName);
        dto.setCouponId(couponId);
        DESUtil.setKey("JIsaNHs2ge6yj231ULU");
        accountService.doInvest(session.getId(), session.getAccName(),projectId,
                amount,dto.getVersion(),dto.getProName(),dto.getCouponId());

    }



}
