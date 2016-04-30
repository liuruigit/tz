import com.jhl.dao.AccountDao;
import com.jhl.dao.impl.rmb.ProjectDao;
import com.jhl.db.SQLOperator;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.Project;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.SQLCondition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amoszhou on 16/1/28.
 */
public class ProjectDaoTest extends BaseTest {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private AccountDao accountDao;


    @Test
    public void testAccount() throws Exception {
        Account account = accountDao.queryById(Account.class,1);
        account.setEmail("39128738912");
        accountDao.update(account);
        System.out.println(account);

    }

    /**
     * Just test for  Test framework
     * @throws Exception
     */
    @Test
    public void testQuery() throws Exception {
        Project project = projectDao.queryById(Project.class, 1);
        System.out.println(project);

    }

    @Test
    public void testPageQuery() throws Exception {
        List<SQLCondition> sqlConditions = new ArrayList<>();
        sqlConditions.add(new SQLCondition("i.NO", SQLOperator.equal,"2016012316013400005"));
        PageInfo info = new PageInfo();
        info.setPageSize(10);
        info.setStartRow(1);
        info.setOffset("213");
        System.out.println(projectDao.queryForPageMap(sqlConditions, info, "order by open_date"));

    }
}
