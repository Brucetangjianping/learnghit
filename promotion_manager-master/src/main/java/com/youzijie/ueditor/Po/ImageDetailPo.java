package com.youzijie.ueditor.Po;

/**
 * Created by lei on 2015/7/20.
 */
public class ImageDetailPo {
    private String url;
    private String source;
    private String state;

    public ImageDetailPo() {
    }

    public ImageDetailPo(String url, String source) {
        this.url = url;
        this.source = source;
        this.state = "SUCCESS";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
