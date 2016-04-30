package com.jhl.dao.impl.rmb;

/**
 * Created by hallywu on 16/1/28.
 * SQL容器
 */
public class SqlContainer {

    //推荐标的
    public static final String PRO_RECOMMEND =  "SELECT " +
                                                "p.`ID`," +
                                                "i.`LOCATION`," +
                                                "p.`NO`," +
                                                "p.AMOUNT," +
                                                "p.ANNUAL_RATE," +
                                                "p.DAYS," +
                                                "i.MARKET_PRICE " +
                                                "FROM " +
                                                "t_project p," +
                                                "t_project_info i " +
                                                "WHERE " +
                                                "p.`ID` = i.`PRO_ID` " +
                                                "AND p.RECOMMEND = 0 and p.status = 1 and p.end_date > ? and p.DELETE_STATE = 1 and p.SELLED_AMOUNT < p.AMOUNT order by p.create_time desc limit 1";
    //标的列表
    public static final String PRO_LIST =       "select p.STATUS,p.`ID`,p.`END_DATE`, FLOOR(p.SELLED_AMOUNT * 100 / p.AMOUNT) AS perc," +
            "                                   i.PROPERTY_TYPE,p.`NO`,p.`MIN`," +
                                                "p.GUARANTEE,i.LOCATION,p.AMOUNT," +
                                                "p.SELLED_AMOUNT,p.ANNUAL_RATE,p.DAYS " +
                                                "from t_project p left join t_project_info i on p.`ID` = i.`PRO_ID`";
    //标的详情
    public static final String PRO_INFO =       "SELECT "+
                                                "p.`NO`,"+
                                                "p.`MIN`,"+
                                                "p.`LIMIT`,"+
                                                "p.`STEP`,"+
                                                "p.ANNUAL_RATE,"+
                                                "p.AMOUNT,"+
                                                "p.DAYS,"+
                                                "i.`NAME`,"+
                                                "FLOOR(p.SELLED_AMOUNT * 100 / p.AMOUNT) AS perc,"+
                                                "(p.AMOUNT - p.SELLED_AMOUNT) AS active_amount,"+
                                                "p.END_DATE,"+
                                                "p.GUARANTEE,"+
                                                "i.LOCATION,"+
                                                "i.AREA,"+
                                                "i.PROPERTY_TYPE,"+
                                                "i.EXTRA_INFO,"+
                                                "i.MARKET_PRICE,"+
                                                "i.SELL_PRICE,"+
                                                "i.PROPERTY_RIGHT,"+
                                                "i.PROPERTY_CERT,"+
                                                "i.PROPERTY_OWNER,"+
                                                "i.LAND_CERT,"+
                                                "i.RECORD "+
                                                "FROM "+
                                                "t_project p "+
                                                "LEFT JOIN t_project_info i ON p.`ID` = i.`PRO_ID` "+
                                                "WHERE "+
                                                "p.id = ?";



    //分页查询用户投资券
    public static final String ACC_COUPON_PAGE = "";
    public static final String QUERY_INVEST_RECORD = "SELECT\n" +
            "\ti.`NAME`,\n" +
            "\to.`STATUS`,\n" +
            "\tp.`NO`,\n" +
            "\to.AMOUNT / 100 AS Amount,\n" +
            "\tp.ANNUAL_RATE,\n" +
            "\to.CREATE_TIME,\n" +
            "\to.SUCC_TIME\n" +
            "from\n" +
            "\tt_invest_order o\n" +
            "LEFT JOIN t_project p ON o.PRO_ID = p.ID\n" +
            "LEFT JOIN t_project_info i ON p.ID = i.PRO_ID";

    //标的详情页
    public static final String PRO_ATTACH = "select a.`NAME`,a.`DESC`,a.`URL` from t_project_attach_file a  where a.PRO_ID = ? and delete_state = 1";
    //标的投资记录
    public static final String PRO_INVEST = "select ACC_NAME,CREATE_TIME,AMOUNT / 100 as AMOUNT from t_invest_order";

    //按月查询交易记录
    public static final String BILLING_MONTH = "SELECT c.acc_money / 100 as money,c.amount / 100 as amount," +
            "c.create_time,CONCAT(tran_name,'-',extra_msg1),c.extra_msg4 as statusDesc,c.order_no from t_account_change c";
}







