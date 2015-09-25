package com.jami.util;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by felixzhao on 14-4-15.
 */
public class Page<PoType> {

    private List<PoType> elements = new LinkedList<PoType>();
    private int pageNo;
    private int pageSize;
    private long rowCount;

    public List<PoType> getElements() {
        return elements;
    }

    public void setElements(List<PoType> elements) {
        this.elements = elements;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public void setPageAttr(int pn, int ps, long rows) {
        this.setPageNo(pn);
        this.setRowCount(rows);
        this.setPageSize(ps);
    }

    public long getPageTotal() {
        if (pageSize > 0) {
            return (rowCount + pageSize - 1) / pageSize;
        } else {
            return 0;
        }
    }
}
