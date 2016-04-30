import com.jhl.constant.ConfigKey;
import com.jhl.oss.OssManageUtil;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.InvestOrder;
import com.jhl.pojo.biz.Project;
import com.jhl.service.*;
import com.jhl.util.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.util.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/25.
 */
public class AccountServiceTest extends BaseTest {

    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;
    @Autowired
    InviteRewardConfigService inviteRewardConfigService;
    @Autowired
    ContractGenerateService contractGenerateService;

    @Test
    @ExpectedException(RuntimeException.class)
    public void jhTest() {
        try {
            String res = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @ExpectedException(RuntimeException.class)
    public void messageTest() {
        try {
            Account account = new Account();
            account.setRealName("aaaa");
            account.setMobile("18929353864");
            messageService.sendRechargeSuccess(100000,"充值",account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @ExpectedException(RuntimeException.class)
    public void configTest() {
        try {
            Account account = accountService.queryById(88);
            inviteRewardConfigService.reWardRecommendAcc(account,5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @ExpectedException(RuntimeException.class)
    public void testInvest4MoreThanLimit() {
        try {
            Account account = accountService.queryById(34);
            double money = 1000;
            Long cent = new Money(money).getCent();
            System.out.println(account.getMoney());
            System.out.println(cent);
            System.out.println(cent > account.getMoney());
//            account.setTradePwd(PasswordUtil.generate("123456"));
//            accountService.update(account);
            Assert.isTrue(PasswordUtil.verify("123456",account.getTradePwd()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @ExpectedException(RuntimeException.class)
    public void doInvest() {
        try {
            Account account = accountService.queryById(44);
            DESUtil.setKey("JIsaNHs2ge6yj231ULU");
            SessionUtil.setSession(account);
//
//            accountService.doInvest(account.getId(),account.getAccName(),17,1000,"1.0.1","TEST","");
//            contractGenerateService.getSupplier("17");
            Map<String,Object> root = new HashMap<String,Object>();
            String gateway = SysConfig.geteConfigByKey(ConfigKey.STATIC_APP_DOMAIN);
            root.put("date", DateUtil.formatToday());
            InvestOrder order = new InvestOrder();
            order.setAmount(1000l);
            Project project = new Project();
            project.setAmount(59999l);
            long perc = order.getAmount() / project.getAmount() / 100l;
            root.put("perc", NumberUtil.rffi_str((double) perc));
            root.put("gateway", "http://www.jinhulu.com/generate/html/");
            root.put("amount", order.getAmount() / 100);
            root.put("account", account);
            root.put("spm", contractGenerateService.getSupplier("17"));
            File file = new File("upload/contract" + "/" +order.getProId() + "/" +order.getAccId()+"_"+ order.getNo() + ".html");
            Freemarker.printFile("invest.ftl",root,file,"contract");
            OssManageUtil.uploadFile(file,"upload/contract" + "/" + DateUtil.formatToday());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @ExpectedException(RuntimeException.class)
    public void idCard() {
        try {
            Account account = accountService.queryAccByIdNo("352230198901040016");
            Assert.isNull(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @ExpectedException(RuntimeException.class)
    public void msgTest() {
        try {
            Account account = accountService.queryById(82);
            messageService.sendSmsAndNotice("dsa","dsadasd","dasdhiuosa",account);
            Assert.isNull(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
