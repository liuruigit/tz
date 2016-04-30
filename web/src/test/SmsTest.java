import com.jhl.service.SmsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/2/25.
 */
public class SmsTest extends BaseTest {

    @Autowired
    SmsService smsService;

    @Test
    public void test(){
        try {
//            smsService.sendRegistCode("18929353864");
            Double a = Double.parseDouble("1.0");
            Double b = Double.parseDouble("2.0");
            System.out.println(a.compareTo(b));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
