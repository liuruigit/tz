package com.jhl.common.constant;

/**
 * Created by vic wu on 2015/8/19.
 */
public interface CommonConstant {

    public static final String CLIENT_APP = "APP";
    public static final String CLIENT_WEB = "WEB";

    public static final int PAGE_SIZE = 10;
    public static final int PAGE_BENIFIT_SIZE = 5;
    public static final int JSON_BACK_NO_AUTH = 999;
    public static final int JSON_BACK_REPEAT_LOGIN = 777;
    public static final int JSON_BACK_NO_REAL_NAME = 888;
    public static final int JSON_BACK_SUCCESS = 0;
    public static final int JSON_BACK_FAILED = 1;

    public static final int IS_CERT_SHOP_ACC_Y = 0;
    public static final int IS_CERT_SHOP_ACC_N = 1;

    public static final int MAX_LOGIN_FAILED_TIMES = 3;
    public static final long SESSION_EXPIRED_TIMES = 1000 * 60 * 60;

    public static final String BASE_DOMAIN = "http://hallywu86.6655.la:8081/web/";
    public static final String FILEPATHFILE = "uploadFiles/file/";		//文件上传路径
}
