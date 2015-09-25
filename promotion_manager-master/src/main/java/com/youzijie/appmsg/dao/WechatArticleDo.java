package com.youzijie.appmsg.dao;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by lei on 2015/7/16.
 */
public class WechatArticleDo {
    private Integer id;
    private Integer msgId;

    private String author;
    private String title;
    private String cover;
    private String content;
    private String content_source_url;
    private String digest;

    private static final String filepath = "/data/jweb_static/jweb_promotion_manager/content/";

    public void copy(WechatArticleDo wechatArticleDo) {
        this.author = wechatArticleDo.author;
        this.title = wechatArticleDo.title;
        this.cover = wechatArticleDo.cover;
        this.content = wechatArticleDo.content;
        this.content_source_url = wechatArticleDo.content_source_url;
        this.digest = wechatArticleDo.digest;
    }

    private String getFileName() {
        return filepath + this.id + ".html";
    }

    public boolean saveContent() {
        String filename = getFileName();
        File path = new File(filename);
        try {
            FileUtils.writeStringToFile(path, this.content);
            this.content = filename;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadContent() {
        File path = new File(this.content);
        try {
            this.content = FileUtils.readFileToString(path, "UTF-8");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_source_url() {
        return content_source_url;
    }

    public void setContent_source_url(String content_source_url) {
        this.content_source_url = content_source_url;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
