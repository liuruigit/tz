package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import com.jhl.annotation.Transient;
import com.jhl.util.DESUtil;
import com.jhl.util.MD5Util;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * 类名称：Account.java
 * 类描述：
 *
 * @author Hally
 *         作者单位：
 *         联系方式：18929353864
 *         创建时间：2016-01-15
 * @version 1.0
 */
public class Account implements Serializable {
    @Transient
    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    @PrimaryKey
    private Integer id;

    private Integer vip;

    /**
     * 推荐人ID
     */
    private int recommendId;
    /**
     * 账号
     *
     */
    private String accName;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 交易密码
     */
    private String tradePwd;
    /**
     * 手势密码
     */
    private String linePwd;
    /**
     * token
     */
    private String token;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 1正常0删除
     */
    private Integer deleteState;
    /**
     * 身份证号
     */
    private String certNo;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 投资前准备步骤0未开始1已实名2已设置交易密码3已绑卡
     */
    private Integer prepareStep;
    /**
     * 余额
     */
    private Long money = 0L;
    /**
     * 冻结金额
     */
    private Long frozenMoney = 0L;

    /**
     * 投资中的金额
     */
    private Long investMoney = 0L;
    /**
     * 累计收益
     */
    private Long accIncome = 0L;

    /**
     * 摘要
     */
    private String digest;

    /**
     * 版本
     */
    private Integer version = 0;

    /**
     * 积分
     */
    private Integer point = 0;

    private Integer frozenPoint = 0;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 指纹密码
     */
    private String fingerPwd;

    private String openId;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 上次登录地址
     */
    private String lastLoginPos;

    /**
     * 上次登录IMEI
     */
    private String lastLoginImei;

    public String getLastLoginImei() {
        return lastLoginImei;
    }

    public void setLastLoginImei(String lastLoginImei) {
        this.lastLoginImei = lastLoginImei;
    }

    public String getLastLoginPos() {
        return lastLoginPos;
    }

    public void setLastLoginPos(String lastLoginPos) {
        this.lastLoginPos = lastLoginPos;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 数据解密
     *
     * @param account
     * @return
     */
    public Account parseAccout(Account account) {
        if (account == null) return null;
        System.out.println(account.toString());
        account.setMobile(DESUtil.getDesString(account.getMobile()));
        account.setAccName(DESUtil.getDesString(account.getAccName()));
        account.setEmail(DESUtil.getDesString(account.getEmail()));
        account.setRealName(DESUtil.getDesString(account.getRealName()));
        account.setCertNo(DESUtil.getDesString(account.getCertNo()));
        return account;
    }

    public void parseAccout() {
        logger.info("解密数据开始：{}",this.toString());
        this.mobile = DESUtil.getDesString(mobile);
        this.accName = DESUtil.getDesString(accName);
        this.email = DESUtil.getDesString(email);
        this.realName = DESUtil.getDesString(realName);
        this.certNo = DESUtil.getDesString(certNo);
        this.linePwd = DESUtil.getDesString(linePwd);
    }

    /**
     * 数据加密
     * @return
     */
    public void encrptAccout() {
        logger.info("加密数据开始：{}",this.toString());
        this.mobile = DESUtil.getEncString(mobile);
        this.accName = DESUtil.getEncString(accName);
        this.email = DESUtil.getEncString(email);
        this.realName = DESUtil.getEncString(realName);
        this.certNo = DESUtil.getEncString(certNo);
        this.linePwd = DESUtil.getEncString(linePwd);
    }

    /**
     * 生成当前帐户的摘要信息
     *
     * @return
     */
    public String generateDigest() {
        String origin = this.getId() + ":" + this.getMoney() + ":" + this.getInvestMoney()
                + ":" + this.getFrozenMoney() + ":" + this.getAccIncome();
        try {
            String firstTime = MD5Util.getMD5String(origin);
            return MD5Util.getMD5String(firstTime);
        } catch (Exception e) {
        }
        return "";
    }

    public enum PrepareInvestStep{
        INIT(0),VALIDATE_REALNAME(1),TRADE_PWD(2),BIND_CARD(3);

        PrepareInvestStep(int val){
            this.value = val;
        }

        private int value;

        public int getValue() {
            return value;
        }
    }

    public Integer getFrozenPoint() {
        return frozenPoint;
    }

    public void setFrozenPoint(Integer frozenPoint) {
        this.frozenPoint = frozenPoint;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public boolean modifiedByException() {
//        return !this.generateDigest().equals(this.getDigest());
        return false;
    }

    public Long getAccIncome() {
        return accIncome;
    }

    public void setAccIncome(Long accIncome) {
        Long temp = accIncome == null ? 0L:accIncome;
        this.accIncome = temp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(int recommendId) {
        this.recommendId = recommendId;
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

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getLinePwd() {
        return linePwd;
    }

    public void setLinePwd(String linePwd) {
        this.linePwd = linePwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getPrepareStep() {
        return prepareStep;
    }

    public void setPrepareStep(Integer prepareStep) {
        this.prepareStep = prepareStep;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        Long temp = money ==null ? 0L:money;
        this.money = temp;
    }

    public Long getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(Long frozenMoney) {
        Long temp = frozenMoney ==null ? 0L:frozenMoney;
        this.frozenMoney = temp;
    }

    public Long getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(Long investMoney) {
        Long temp = investMoney ==null ? 0L:investMoney;
        this.investMoney = temp;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getFingerPwd() {
        return fingerPwd;
    }

    public void setFingerPwd(String fingerPwd) {
        this.fingerPwd = fingerPwd;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}










