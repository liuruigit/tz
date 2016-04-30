package com.jhl.util;

import java.text.DecimalFormat;

/**
 * Created by xin.fang on 2015/8/18.
 */
public class NumberUtil {

    public static int getIntMax(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    public static String rffi_str(long num){
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(false);
        return df.format(num);
    }

    public static String rffi_str(Double num){
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(false);
        return df.format(num);
    }

    /**
     * 保留两位小数，舍入
     * @param num
     * @return
     */
    public static Double rffi(Double num){
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(false);
        return Double.parseDouble(df.format(num));
    }

    /**
     * 保留两位小数，截断
     * @param d
     * @return
     */
    public static Double format_double(double d){
        return Math.floor(d*100d)/100;
    }
}
