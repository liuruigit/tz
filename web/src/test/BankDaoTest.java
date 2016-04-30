
import com.jhl.dao.BankDao;
import com.jhl.pojo.biz.BankCard;
import com.jhl.service.BankCardService;
import com.jhl.util.DESUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by hallywu on 16/1/30.
 */
public class BankDaoTest extends BaseTest {

    @Autowired
    BankCardService bankCardService;
    @Autowired
    BankDao bankDao;

    @Test
    public void addTest(){
        BankCard bankCard = new BankCard();
        bankCard.setIsDefault(1);
        bankCard.setName("测试");
        bankCard.setPrcptcd("测试");
        bankCard.setBankName("测试");
        bankCard.setBankCardNo("测试");
        bankCard.setBranch("测试");
        bankCard.setCity("测试");
        bankCard.setCreateTime(new Date());
        bankCard.setNoAgree("测试");
        bankCard.setUserId(1);
        try {
            bankCardService.save(bankCard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryBankRuleTest() throws Exception {
        org.springframework.util.Assert.isNull(bankDao.queryBankRuleByBankName("招商银行"));
    }

    @Test
    public void encryptTest() throws Exception {
        BankCard bankCard = bankCardService.queryById(48);
        bankCard.encrptBank();
        bankCardService.update(bankCard);
    }
}
