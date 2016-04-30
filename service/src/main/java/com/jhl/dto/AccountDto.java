package com.jhl.dto;

/**
 * Created by Administrator on 2016/2/19.
 */
public class AccountDto extends BaseDto {
    String accName;
    String pwd;
    String realName;
    String mobile;
    String certNo;
    String content;
    String safeQues;
    String quesId;
    String answer;
    String recommendMobile;
    String linePwd;
    String openId;

    public String getLinePwd() {
        return linePwd;
    }

    public void setLinePwd(String linePwd) {
        this.linePwd = linePwd;
    }

    public String getRecommendMobile() {
        return recommendMobile;
    }

    public void setRecommendMobile(String recommendMobile) {
        this.recommendMobile = recommendMobile;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getSafeQues() {
        return safeQues;
    }

    public void setSafeQues(String safeQues) {
        this.safeQues = safeQues;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "accName='" + accName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", certNo='" + certNo + '\'' +
                ", content='" + content + '\'' +
                ", safeQues='" + safeQues + '\'' +
                ", quesId='" + quesId + '\'' +
                ", answer='" + answer + '\'' +
                ", recommendMobile='" + recommendMobile + '\'' +
                ", linePwd='" + linePwd + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
