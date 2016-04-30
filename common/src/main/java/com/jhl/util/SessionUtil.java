package com.jhl.util;

import com.google.common.base.Strings;
import com.jhl.pojo.biz.Account;

/**
 * Created by vic wu on 2015/8/26.
 * Req获取Session
 */
public class SessionUtil {

    static ThreadLocal<Account> sessionContainer = new ThreadLocal<Account>();
    static ThreadLocal<String> noContainer = new ThreadLocal<String>();

    /**
     * 从主线程的sessionContainer中获取session，新创建的现成无法获取
     * @return
     */
    public static Account getSession(){
        return sessionContainer.get();
    }

    public static void setSession(Account session){
        session.parseAccout();
        sessionContainer.set(session);
    }

    public static String getNo(){
        String no = noContainer.get();
        if (Strings.isNullOrEmpty(no)){
            no = SeqNoUtil.nextSeqNo();
            if (getSession() != null) {
                no += ":" + getSession().getId();
            }
            setNo(no);
            return no;
        }else {
            return no;
        }
    }

    public static void setNo(String no){
        noContainer.set(no);
    }
}
