package com.youzijie.ueditor.Po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2015/7/20.
 */
public class ConfigPo {
    private String imageUrl;
    private String imagePath;
    private String imageFieldName;
    private int imageMaxSize;
    private List<String> imageAllowFiles;

    public ConfigPo() {
        this.imageUrl = "imageUrl\": \"http://localhost/ueditor/php/controller.php?action=uploadimage";
        this.imagePath = "/ueditor/php/";
        this.imageFieldName = "upfile";
        this.imageMaxSize = 2048;
        this.imageAllowFiles = new ArrayList<String>();
        this.imageAllowFiles.add(".jpg");
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageFieldName() {
        return imageFieldName;
    }

    public void setImageFieldName(String imageFieldName) {
        this.imageFieldName = imageFieldName;
    }

    public int getImageMaxSize() {
        return imageMaxSize;
    }

    public void setImageMaxSize(int imageMaxSize) {
        this.imageMaxSize = imageMaxSize;
    }

    public List<String> getImageAllowFiles() {
        return imageAllowFiles;
    }

    public void setImageAllowFiles(List<String> imageAllowFiles) {
        this.imageAllowFiles = imageAllowFiles;
    }
}
