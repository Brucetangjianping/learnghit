package com.youzijie.ueditor.Po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2015/7/20.
 */
public class CatchImagePo {
    private String state;
    private List<ImageDetailPo> list;

    public CatchImagePo() {
        this.state = "SUCCESS";
        this.list = new ArrayList<ImageDetailPo>();

        //this.list.add(new ImageDetailPo("upload/1.jpg", "http://b.com/2.jpg"));
        //this.list.add(new ImageDetailPo("upload/2.jpg", "http://b.com/2.jpg"));
    }

    public void addImage(ImageDetailPo imageDetailPo) {
        this.list.add(imageDetailPo);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ImageDetailPo> getList() {
        return list;
    }

    public void setList(List<ImageDetailPo> list) {
        this.list = list;
    }
}
