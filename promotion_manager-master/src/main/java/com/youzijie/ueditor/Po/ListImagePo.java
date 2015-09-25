package com.youzijie.ueditor.Po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2015/7/20.
 */
public class ListImagePo {
    private String state;
    private List<ImagePo> list;
    private int start;
    private int total;

    public ListImagePo() {
        this.state = "SUCCESS";
        this.list = new ArrayList<ImagePo>();
        /*this.list.add(new ImagePo("upload/1.jpg"));
        this.list.add(new ImagePo("upload/2.jpg"));
        this.start = 20;
        this.total = 100;*/
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ImagePo> getList() {
        return list;
    }

    public void setList(List<ImagePo> list) {
        this.list = list;
    }

    public void addList(ImagePo image) {
        this.list.add(image);
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
