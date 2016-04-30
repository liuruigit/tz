import com.jhl.oss.OSSConfigure;
import com.jhl.oss.OssManageUtil;
import org.junit.Test;

import java.io.File;

/**
 * Created by hallywu on 16/4/6.
 */
public class OssTest extends BaseTest {

    @Test
    public void test() throws Exception {
        OSSConfigure configure = new OSSConfigure("oss.properties");
        OssManageUtil.uploadFile(configure,new File("/Users/hallywu/Pictures/蜜月/IMG_0008.jpg"),"test/hally");
    }

}
