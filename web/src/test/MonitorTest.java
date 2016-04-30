import com.jhl.dao.impl.rmb.ProjectDao;
import com.jhl.service.AccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by amoszhou on 16/1/28.
 */
public class MonitorTest extends BaseTest {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private AccountService accountService;

    @Test
    public void testDaoAspect() throws Exception {
        projectDao.updateStatusIfFull(1);
//        accountService.doInvest(1,1,new Money(10000));
    }
}
