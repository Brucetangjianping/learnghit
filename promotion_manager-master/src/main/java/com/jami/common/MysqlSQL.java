package com.jami.common;

import org.apache.ibatis.jdbc.AbstractSQL;

/**
 * Created by felixzhao on 14-5-28.
 */
public class MysqlSQL extends AbstractSQL<MysqlSQL> {

    private String limitStr = "";

    @Override
    public MysqlSQL getSelf() {
        return this;
    }

    public MysqlSQL LIMIT(int startIndex, int endIndex) {
        limitStr = " limit " + startIndex + ", " + endIndex;
        return getSelf();
    }

    @Override
    public String toString(){
        String str = super.toString();
        return str + limitStr;
    }
}
