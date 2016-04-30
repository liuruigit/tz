
import com.jhl.common.BalanceChangeType;
import com.jhl.util.SessionUtil;
import com.jhl.dao.AccountDao;
import com.jhl.dao.impl.rmb.ProjectDao;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.Project;
import com.jhl.service.AccountService;
import com.jhl.util.Money;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

/**
 * Created by amoszhou on 16/1/29.
 */
public class AccountControllerTest extends BaseTest {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProjectDao projectDao;

    @Autowired
//    private AccountController accountController;

    @Test
    @ExpectedException(RuntimeException.class)
    public void testUpdateMoneyResY2hs() throws Exception{
        Account account = accountDao.queryById(Account.class,34);
//        account.setMoney(100000000l);
        accountService.updateUserBalance(34,new Money(10000000L), BalanceChangeType.CHARGE,"Test2391238123","Test2391238123");
//
//        account.setId(1);
//        SessionUtil.setSession(account);
//        accountController.invest(1, 999999L, "IOS");
    }

    @Test
    @ExpectedException(RuntimeException.class)
    public void testInvest4MoreThanLimit() {
        Account account = new Account();
        account.setId(1);
        SessionUtil.setSession(account);
//        accountController.invest(1, 30000001L, "IOS");
    }

    @Test
    @ExpectedException(RuntimeException.class)
    public void testInvest4MoreThanProjectTotalAmount() {
        Account account = new Account();
        account.setId(1);
        SessionUtil.setSession(account);
//        accountController.invest(1, 50000000001L, "IOS");
    }

    @Test
    @ExpectedException(RuntimeException.class)
    public void testInvest4BalanceNotEnough() {
        Account account = new Account();
        account.setId(1);
        SessionUtil.setSession(account);
//        accountController.invest(1, 100000001L, "IOS");
    }


    @Test
    public void testNormalInvest() throws Exception {
        Account account = accountDao.queryById(Account.class, 1);
        SessionUtil.setSession(account);

        Project project = projectDao.queryById(Project.class, 1);

        long amountBeforeModify = account.getMoney();
        long frozenMoneyBeforeModify = account.getInvestMoney();

        long investedAmountBeforeModify = project.getSelledAmount();


        Money investAmount = new Money(10000000);
//        accountController.invest(1, investAmount.getAmount().doubleValue(), "IOS");

        Account accountAfterModify = accountDao.queryById(Account.class, 1);
        Project projectAfterModify = projectDao.queryById(Project.class, 1);

        /**
         * 投资前后帐户总金额一致
         */
        Assert.assertEquals(amountBeforeModify, accountAfterModify.getMoney() + investAmount.getCent());
        Assert.assertEquals(accountAfterModify.getInvestMoney().longValue(), frozenMoneyBeforeModify + investAmount.getCent());

        /**
         * 项目总金额一致
         */
        Assert.assertEquals(projectAfterModify.getSelledAmount().longValue(), investedAmountBeforeModify + investAmount.getCent());

    }

    @Test
    public void testInsert() throws Exception {
        Account account = new Account();
        account.setAccName("aaaa");
        accountDao.add(account);
    }

    @Test
    public void testStringBuilder() throws Exception {
        Account account = accountDao.queryById(Account.class, 1);
        System.out.println(account.generateDigest());
    }

}
