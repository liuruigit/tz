import com.jhl.common.constant.CommonConstant;
import com.jhl.pojos.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by amoszhou on 16/1/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:spring-dispatcher.xml"})
    public abstract class BaseTest {

    protected PageInfo getPageInfo(String page){
        int pageNum = CommonConstant.PAGE_SIZE;
        int currentPage = 1;
        if (StringUtils.isNotBlank(page) && StringUtils.isNumeric(page)) {
            currentPage = Integer.parseInt(page);
            if (currentPage < 1) {
                currentPage = 1;
            }
        }
        int startRow = (currentPage - 1) * pageNum;
        return new PageInfo(startRow, pageNum);
    }
}
