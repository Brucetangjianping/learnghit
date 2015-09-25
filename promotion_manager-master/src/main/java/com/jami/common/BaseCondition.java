package com.jami.common;

/**
 * Created by felixzhao on 14-5-28.
 */
public class BaseCondition {

    private int pageSize = 10;

    private int pageNo = 1;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
