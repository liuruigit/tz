package com.jhl.pojo.session;


/**
 * Created by vic wu on 2015/8/14.
 */
public class UserSession {

//    public static final int IS_REALNAME_STEP_1 = 3;//已实名
//    public static final int IS_REALNAME_STEP_2 = 2;//已经上传身份证
//    public static final int IS_REALNAME_NO = 1;//未实名
//
//    public static final int USER_TYPE_NORMAL = 1;//投资人
//    public static final int USER_TYPE_SHOP = 2;//认证商城用户
//    public static final int USER_TYPE_DRIVER = 3;//司机
//    public static final int USER_TYPE_CHECK = 5;//审核中
//
//    private String last_login_imei;
//    private Long expiredTime;
//    private String userName;
//    private String realName;
//    private String token;
//    private String cacheKey;
//    private String idCardNo;
//    private String tradePwd;
//    private int userId;
//    private int userType;
//    private String partUserId1;
//    private String mobile;
//    private double money;
//    private double freezeMoney;
//    private int isNew;
//    private Date regisTime;
//
//    public Date getRegisTime() {
//        return regisTime;
//    }
//
//    public void setRegisTime(Date regisTime) {
//        this.regisTime = regisTime;
//    }
//
//    public int getIsNew() {
//        return isNew;
//    }
//
//    public void setIsNew(int isNew) {
//        this.isNew = isNew;
//    }
//
//    public String getTradePwd() {
//        return tradePwd;
//    }
//
//    public void setTradePwd(String tradePwd) {
//        this.tradePwd = tradePwd;
//    }
//
//    public int getUserType() {
//        return userType;
//    }
//
//    public void setUserType(int userType) {
//        this.userType = userType;
//    }
//
//    public double getFreezeMoney() {
//        return freezeMoney;
//    }
//
//    public void setFreezeMoney(double freezeMoney) {
//        this.freezeMoney = freezeMoney;
//    }
//
//    public String getRealName() {
//        return realName;
//    }
//
//    public void setRealName(String realName) {
//        this.realName = realName;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public int getPrepareStep() {
//        return isRealName;
//    }
//
//    public void setPrepareStep(int isRealName) {
//        this.isRealName = isRealName;
//    }
//
//    public String getIdCardNo() {
//        return idCardNo;
//    }
//
//    public void setIdCardNo(String idCardNo) {
//        this.idCardNo = idCardNo;
//    }
//
//    private int isRealName;
//
//    public String getPartUserId1() {
//        return partUserId1;
//    }
//
//    public void setPartUserId1(String partUserId1) {
//        this.partUserId1 = partUserId1;
//    }
//
//    public UserSession(){}
//
//    public UserSession(String uuid,String last_login_imei, String userName, int userId,String
//            partUserId1,int isRealName,String idCardNo,String realName,String mobile,int isCertShopAcc) throws Exception {
//        this.cacheKey = MD5Util.getMD5String(uuid + last_login_imei);
//        this.token = uuid;
//        this.last_login_imei = last_login_imei;
//        this.userName = userName;
//        this.expiredTime = DateUtil.getNowTime() + 24 * 60 * 60 * 1000;//创建之后30分钟过期
//        this.userId = userId;
//        this.partUserId1 = partUserId1;
//        this.isRealName = isRealName;
//        this.idCardNo = idCardNo;
//        this.realName = realName;
//        this.mobile = mobile;
//    }
//
//    public static String genKey(User user) throws Exception {
//        return MD5Util.getMD5String(user.getToken() + user.getLastLoginImei());
//    }
//
//    public UserSession(User user){
////        System.out.println("缓存刷新:"+user.toString());
//        this.cacheKey = user.getToken() + user.getLastLoginImei();
//        this.token = user.getToken();
//        this.last_login_imei = user.getLastLoginImei();
//        this.userName = user.getAccName();
//        this.userId = user.hashCode();
//        this.isRealName = user.getPrepareStep();
//        this.idCardNo = user.getCertNo();
//        this.realName = user.getRealName();
//        this.mobile = user.getMobile();
//        this.money = user.getMoney();
//        this.freezeMoney = user.getFreezeMoney();
//        this.tradePwd = user.getTradePwd();
//        this.regisTime = user.getCreateTime();
//    }
//
//    public double getMoney() {
//        return money;
//    }
//
//    public void setMoney(double money) {
//        this.money = money;
//    }
//
//    public String getCacheKey() {
//        return cacheKey;
//    }
//
//    public void setCacheKey(String cacheKey) {
//        this.cacheKey = cacheKey;
//    }
//
//    public String getLast_login_imei() {
//        return last_login_imei;
//    }
//
//    public void setLast_login_imei(String last_login_imei) {
//        this.last_login_imei = last_login_imei;
//    }
//
//    public Long getExpiredTime() {
//        return expiredTime;
//    }
//
//    public void setExpiredTime(Long expiredTime) {
//        this.expiredTime = expiredTime;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
}
