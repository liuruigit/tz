package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.constant.ConfigKey;
import com.jhl.dao.impl.rmb.SecurityQuestionDao;
import com.jhl.dto.AccountDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.SecurityQuestion;
import com.jhl.proxy.impl.jyt.util.StringUtil;
import com.jhl.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/2.
 * 密码保护问题
 */
@Service(value = "webSecurityQuestionService")
public class SecurityQuestionService extends BaseService<SecurityQuestion> {

    @Autowired
    SecurityQuestionDao dao;

    private List<Map<String,String>> sec = new ArrayList<Map<String,String>>();

    /**
     * 查询默认的系统配置
     * @return
     */
    public void initSec(){
        List<Map<String,String>> ques = new ArrayList<Map<String,String>>();

        Map<String,String> q1 = new HashMap<String,String>();
        Map<String,String> q2 = new HashMap<String,String>();
        Map<String,String> q3 = new HashMap<String,String>();
        Map<String,String> q4 = new HashMap<String,String>();
        Map<String,String> q5 = new HashMap<String,String>();
        Map<String,String> q6 = new HashMap<String,String>();

        q1.put("key",ConfigKey.SAFE_QEST_1);
        q2.put("key",ConfigKey.SAFE_QEST_2);
        q3.put("key",ConfigKey.SAFE_QEST_3);
        q4.put("key",ConfigKey.SAFE_QEST_4);
        q5.put("key",ConfigKey.SAFE_QEST_5);
        q6.put("key",ConfigKey.SAFE_QEST_6);

        q1.put("value",SysConfig.geteConfigByKey(ConfigKey.SAFE_QEST_1));
        q2.put("value",SysConfig.geteConfigByKey(ConfigKey.SAFE_QEST_2));
        q3.put("value",SysConfig.geteConfigByKey(ConfigKey.SAFE_QEST_3));
        q4.put("value",SysConfig.geteConfigByKey(ConfigKey.SAFE_QEST_4));
        q5.put("value",SysConfig.geteConfigByKey(ConfigKey.SAFE_QEST_5));
        q6.put("value",SysConfig.geteConfigByKey(ConfigKey.SAFE_QEST_6));

        ques.add(q1);
        ques.add(q2);
        ques.add(q3);
        ques.add(q4);
        ques.add(q5);
        ques.add(q6);

        this.sec = ques;
    }

    public List<Map<String,String>> getSec(){
        if (sec.size() == 0) initSec();
        return sec;
    }

    /**
     * 添加密码查询问题
     * @param dto
     * @param account
     * @throws Exception
     */
    public void addQuestion(AccountDto dto, Account account) throws Exception{
        if (Strings.isNullOrEmpty(dto.getSafeQues()))return;
        String ques = dto.getSafeQues();
        ques = URLDecoder.decode(ques, "UTF-8");
        String[] arr = ques.split("\\|");
        if (arr.length > 10) return;
        List<SecurityQuestion> list = new ArrayList<SecurityQuestion>();
        for(String entry : arr) {
            String[] objArr = entry.split(",");
            if (objArr.length != 2)return;
            SecurityQuestion securityQuestion = buildSecQue(objArr,account);
            list.add(securityQuestion);
        }
        addBatch(list);
    }

    /**
     * 查询接口
     * @param account
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryQuestion(Account account) throws Exception {
        return dao.queryQuestion(account);
    }

    /**
     * 检查安全问题是否正确
     * @param account
     * @param dto
     * @return
     * @throws Exception
     */
    public boolean checkQues(Account account,AccountDto dto) throws Exception {
        if (Strings.isNullOrEmpty(dto.getSafeQues()))return false;
        String base = dto.getSafeQues();
        base = URLDecoder.decode(base, "UTF-8");
        String[] arr = base.split("\\|");
        if (arr.length > 10) return false;
        List<Map<String,Object>> question = dao.queryQuestionAndAnswer(account);
        Map<String,String> quesMap = toMap(question);
        for(String entry : arr) {
            String[] objArr = entry.split(",");
            if (objArr.length != 2)return false;
            String ques = objArr[0];
            String answer = objArr[1];
            if (!StringUtil.equalsIgnoreCase(answer,quesMap.get(ques)))return false;
        }
        return true;
    }

    private Map<String,String> toMap(List<Map<String,Object>> question){
        Map<String,String> result = new HashMap<String,String>();
        for (Map<String,Object> obj : question) {
            String ans = obj.get("ANSWER") == null ? "" : obj.get("ANSWER").toString();
            String ques = obj.get("QUESTION") == null ? "" : obj.get("QUESTION").toString();
            result.put(ques,ans);
        }
        return result;
    }

    /**
     * 更新接口
     * @param dto
     * @throws Exception
     */
    public void updateQuestion(AccountDto dto) throws Exception{
        String ans = dto.getSafeQues();

    }

    private SecurityQuestion buildSecQue(String[] objArr,Account account){
        SecurityQuestion securityQuestion = new SecurityQuestion();
        securityQuestion.setAccId(account.getId());
        securityQuestion.setAnswer(objArr[1]);
        securityQuestion.setQuestion(SysConfig.geteConfigByKey(objArr[0]));
        securityQuestion.setCreateTime(DateUtil.now());
        securityQuestion.setDeleteState(1);
        securityQuestion.setUpdateTime(DateUtil.now());
        return securityQuestion;
    }

}
