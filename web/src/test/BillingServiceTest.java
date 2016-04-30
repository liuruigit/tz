import com.jhl.dto.ChannelOrderDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.Project;
import com.jhl.pojos.PageInfo;
import com.jhl.service.*;
import com.jhl.task.month.MonthBillingTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/20.
 */
public class BillingServiceTest extends BaseTest {

    @Autowired
    BillingService billingService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    ProjectService projectService;
    @Autowired
    MonthBillingTask monthBillingTask;

    @Test
    public void queryByMath(){
        PageInfo pageInfo = getPageInfo("3");
        Account account = new Account();
        account.setId(1);
        ChannelOrderDto channelOrderDto = new ChannelOrderDto();
        channelOrderDto.setType("");
        channelOrderDto.setDate("2016-03");
        channelOrderDto.setOrder("desc");
        channelOrderDto.setOrderName("money");
        try {
            System.out.println(billingService.queryByMonth(pageInfo,account,channelOrderDto).getResultList().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void full(){

        try {
            Project project = projectService.queryById(3);
            transactionService.ensure(project);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryByRange(){
        PageInfo pageInfo = getPageInfo("1");
        Account account = new Account();
        account.setId(1);
        ChannelOrderDto channelOrderDto = new ChannelOrderDto();
        channelOrderDto.setType("");
//        channelOrderDto.setRange("0");
//        channelOrderDto.setRange("1");
//        channelOrderDto.setRange("2");
        channelOrderDto.setRange("1");
        channelOrderDto.setOrder("desc");
        channelOrderDto.setOrderName("money");
        try {
            Map<String,Object> result = new HashMap<String,Object>();
//            result.put("page",billingService.queryByRange(pageInfo,account,channelOrderDto));
//            result.put("charge",billingService.sumChargeAmountByRange(account,channelOrderDto));
            result.put("cash",billingService.sumCashAmountByRange(account,channelOrderDto));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryHlb(){
        PageInfo pageInfo = getPageInfo("1");
        Account account = new Account();
        account.setId(1);
        ChannelOrderDto channelOrderDto = new ChannelOrderDto();
        channelOrderDto.setType("");

        try {
            System.out.println(billingService.queryHlb(pageInfo,account));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryBill(){
        try {
            Account account = accountService.queryById(82);
            System.out.println(billingService.queryBill(account,"201603"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void monthBillingTask(){
        try {
            monthBillingTask.doBillingForAllUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
