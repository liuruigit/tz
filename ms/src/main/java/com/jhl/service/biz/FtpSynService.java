package com.jhl.service.biz;

import com.google.common.base.Strings;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.util.PageData;
import com.jhl.util.SFTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 */
@Service("ftpsynService")
public class FtpSynService {

    @Resource(name="jytbillingService")
    private JytBillingService jytbillingService;
    @Resource(name="trandetailsService")
    private TranDetailsService trandetailsService;
    String DS_HEAD = "DSDZ";//代收
    String DF_HEAD = "DFDZ";//代付
    static final String END_FLAG = "########";//金运通数据结束标示符
    private static Logger logger = LoggerFactory.getLogger(FtpSynService.class);
    static List<String> keysDetail = new ArrayList<String>();
    static List<String> keys = new ArrayList<String>();

    static {
        keys.add("BILLING_DATE");
        keys.add("USER_ID" );
        keys.add("COUNT" );
        keys.add("AMOUNT"  );
        keys.add("SUCC_COUNT");
        keys.add("SUCC_AMOUNT");
        keys.add("FAIL_COUNT" );
        keys.add("FAIL_AMOUNT"   );

        keysDetail.add("TRAN_TYPE"  );
        keysDetail.add("TRAN_DATE"  );
        keysDetail.add("TRAN_DATED" );
        keysDetail.add("ORDER_ID"   );
        keysDetail.add("NUM"        );
        keysDetail.add("REP_ORDER_ID");
        keysDetail.add("TRAN_AMOUT" );
        keysDetail.add("CURRENCY"   );
        keysDetail.add("BANK_NO"    );
        keysDetail.add("BANK_NAME"  );
        keysDetail.add("USER_FIC_NO");
        keysDetail.add("RESULT_NO"  );
        keysDetail.add("RESULT_DESC");
        keysDetail.add("POUND"      );
    }

    public void addDataToPageData(PageData pageData,String key,Object val) throws Exception {
        pageData.put(key,val);
    }

    /**
     * 每天凌晨5点15拉取金运通的对账数据
     */
    @Scheduled(cron = "0 15 5 ? * *")
    public void excuteTask(){
        readRemoteFile(SFTPUtil.instance().downLoad(DS_HEAD),DS_HEAD);
        readRemoteFile(SFTPUtil.instance().downLoad(DF_HEAD),DS_HEAD);
    }

    private int type(String head){
        if (DS_HEAD.equalsIgnoreCase(head)) {
            return ChannelOrder.Type.CHARGE.getValue();
        }else {
            return ChannelOrder.Type.CASH.getValue();
        }
    }

    public String readRemoteFile(InputStream f,String head) {
        String readTxt ="";
        InputStreamReader read = null;
        try {

                read = new InputStreamReader(f,"UTF-8");
                BufferedReader reader = new BufferedReader(read);
                String line = reader.readLine();
                PageData headPageData = buildBillObj(line,keys);
                System.out.println(headPageData);
                if (headPageData == null) {
                    throw new IllegalStateException("解析金运通数据失败");
                }else{
                    headPageData.put("TYPE", type(head));
                }
                jytbillingService.save(headPageData);
                while ((line = reader.readLine()) != null) {
                    if (Strings.isNullOrEmpty(line) || END_FLAG.equalsIgnoreCase(line)){
                        break;
                    }
                    PageData detailPageData = buildBillObj(line,keysDetail);
                    if(detailPageData == null) {
                        throw new IllegalStateException("解析金运通数据失败");
                    }
                    trandetailsService.save(detailPageData);
//                    System.out.println(detailPageData);
                }
                read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(read != null) {
                try {
                    read.close();
                } catch (IOException e) {

                }
            }
        }
        return readTxt;
    }

    /**
     * 解析对账数据
     * @param head
     * @param matchList
     * @return
     * @throws Exception
     */
    private PageData buildBillObj(String head, List<String> matchList) throws Exception{
        if (Strings.isNullOrEmpty(head)) return null;
        PageData pageData = new PageData();

        Object[] keyStrs = matchList.toArray();
        String[] strs = head.split("\\|");
        if (keyStrs.length != strs.length) {
            //// TODO: 2016/3/1 loger
            logger.info("文件数据格式有误！");
            return null;
        }
        for(int i = 0 ; i<strs.length ; i++) {
            addDataToPageData(pageData,keyStrs[i].toString(),strs[i]);
        }
        return pageData;
    }


    public static void main(String[] args) {
//        FtpSynService synUtil = new FtpSynService();
//        synUtil.read("D:\\1.txt");
    }

//    public static void main(String[] args) {
//        Object[] arr = keysDetail.toArray();
//        for (int i = 0 ;i< arr.length;i++) {
//            System.out.println(arr[i]);
//        }
//    }


}
