package com.jhl.db;

/**
 * Created by CodeJ on 2014/9/17.
 */
public enum SQLOperator {
    equal,in,between,greatThan,greatEqualThan,lessThan,lessEqualThan;

    /**
     * 拼装in的参数
     * @param values
     * @return
     */
    public static String inToString(Object[] values) {
        StringBuffer buf = new StringBuffer();
        buf.append(" IN (");
        for(int i = 0;i < values.length;i++) {
            buf.append("?");
            if(i != values.length -1 ) {
                buf.append(",");
            }
        }
        buf.append(") ");
        return buf.toString();
    }
}
