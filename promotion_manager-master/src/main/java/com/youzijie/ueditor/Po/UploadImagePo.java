package com.youzijie.ueditor.Po;

/**
 * Created by lei on 2015/7/20.
 */
public class UploadImagePo {
    private String state;
    private String url;
    private String title;
    private String original;

    public UploadImagePo() {
        this.state ="SUCCESS";
        this.url = "upload/demo.jpg";
        this.title = "demo.jpg";
        this.original = "demo.jpg";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}