package com.sz.jhl.util;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hallywu on 14-11-5.
 * 流水号
 */
public class SeqNoUtil {

    /**
     * 投资订单
     */
    public static final String PREFIX_FARM_INVEST = "I";
    /**
     * 充值订单
     */
    public static final String PREFIX_FARM_CHANNEL = "C";
    /**
     * 一般
     */
    public static final String PREFIX_FARM_DEFAULT = "D";
    /**
     * 转让订单
     */
    public static final String PREFIX_FARM_TRANSFER = "T";
    /**
     * 转让订单
     */
    public static final String PREFIX_FARM_SMS = "SMS";

    private Logger logger = Logger.getLogger(SeqNoUtil.class);

    private static final ReentrantLock lock = new ReentrantLock();

    private static final FastDateFormat ISO_DATE_TIME2_FORMAT = FastDateFormat.getInstance("yyMMddHHmmssSSS");

    private static Map<String, AtomicInteger> countMap = new HashMap<String, AtomicInteger>();

    static {
        countMap.put(PREFIX_FARM_INVEST, new AtomicInteger());
        countMap.put(PREFIX_FARM_CHANNEL, new AtomicInteger());
        countMap.put(PREFIX_FARM_SMS, new AtomicInteger());
        countMap.put(PREFIX_FARM_DEFAULT, new AtomicInteger());

    }


    /**
     * 生成下一个ID，取值范围在1～9999。
     *
     * @return
     */
    public static final int nextId(String prefix) {
        lock.lock();
        AtomicInteger id = countMap.get(prefix);
        try {
            if (id.get() == 99999) {
                id.set(1);
            }
            return id.getAndIncrement();
        } finally {
            lock.unlock();
        }
    }

    public static final String nextShortNo() {
        String a = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        return String.format("%s%03d", a, nextId(PREFIX_FARM_DEFAULT));
    }

    public static final String nextSeqNo() {
        String a = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return String.format("%s%05d", a, nextId(PREFIX_FARM_DEFAULT));
    }

    public static final String nextSeqNo(String prefix) {
        String a = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return prefix + String.format("%s%01d", a, nextId(prefix));
    }

    private static final int hash(String game, String plat) {
        int result = game != null ? game.hashCode() : 0;
        result = 31 * result + (plat != null ? plat.hashCode() : 0);
        return result;
    }


    /**
     * 获取唯一订单号【16位】
     *
     * @return
     */
    public synchronized String createOrderNo(String prefix) {
        String orderNo = toStdDateTime2(new Date());
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
        return prefix + orderNo;
    }

    public static String toStdDateTime2(Date date) {
        return ISO_DATE_TIME2_FORMAT.format(date);
    }
}
