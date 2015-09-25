package com.youzijie.ueditor.svc.impl;

import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.youzijie.ueditor.Po.*;
import com.youzijie.ueditor.svc.EditorService;
import com.youzijie.util.FileHelper;
import com.youzijie.util.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */
@Service
public class EditorServiceImpl implements EditorService {

    private static final String filepath = "/data/jweb_static/jweb_promotion_manager/ueditor/";
    //private static final String filepath = "C:\\Users\\lei\\Pictures";

    @Override
    public ConfigPo getConfig() {
        return new ConfigPo();
    }

    @Override
    public UploadImagePo uploadImage(MultipartFile upfile) throws BusinessException {
        String filename = System.currentTimeMillis() + "";
        filename += ".jpg";

        File dest = new File(filepath + filename);
        try {
            upfile.transferTo(dest);
        } catch (IOException e) {
            throw BusinessException.createInstance(BizErrorCode.UPLOAD_FAILED);
        }
        UploadImagePo uploadImagePo = new UploadImagePo();

        uploadImagePo.setTitle(filename);
        uploadImagePo.setOriginal(upfile.getOriginalFilename());
        uploadImagePo.setOriginal(filename);
        return uploadImagePo;
    }

    @Override
    public UploadScrawlPo uploadScrawl() {
        return new UploadScrawlPo();
    }

    @Override
    public UploadVideoPo uploadVideo(MultipartFile upfile) throws BusinessException {
        String filename = System.currentTimeMillis() + "";
        filename += ".mp4";

        File dest = new File(filepath + filename);
        try {
            upfile.transferTo(dest);
        } catch (IOException e) {
            throw BusinessException.createInstance(BizErrorCode.UPLOAD_FAILED);
        }
        UploadVideoPo uploadVideoPo = new UploadVideoPo();

        uploadVideoPo.setTitle(filename);
        uploadVideoPo.setOriginal(upfile.getOriginalFilename());
        uploadVideoPo.setOriginal(filename);
        return new UploadVideoPo();
    }

    @Override
    public UploadFilePo uploadFile(MultipartFile upfile) throws BusinessException {
        String filename = System.currentTimeMillis() + "";
        filename += ".zip";

        File dest = new File(filepath + filename);
        try {
            upfile.transferTo(dest);
        } catch (IOException e) {
            throw BusinessException.createInstance(BizErrorCode.UPLOAD_FAILED);
        }
        UploadFilePo uploadFilePo = new UploadFilePo();

        uploadFilePo.setTitle(filename);
        uploadFilePo.setOriginal(upfile.getOriginalFilename());
        uploadFilePo.setOriginal(filename);
        return new UploadFilePo();
    }

    @Override
    public ListImagePo listImage(int start, int size) {
        ListImagePo listImagePo = new ListImagePo();
        listImagePo.setStart(start);
        String[] extensions = {"jpg"};
        List<File> files = FileHelper.listFile(filepath, extensions, start, size);

        listImagePo.setTotal(files.size());
        for (File file : files) {
            String path = file.getAbsolutePath();
            listImagePo.addList(new ImagePo(path.replace(filepath, "/")));
        }
        return listImagePo;
    }

    @Override
    public CatchImagePo catchImage(List<String> source) {
        CatchImagePo catchImagePo = new CatchImagePo();
        for (String url : source) {
            String filePath = downloadFile(url);
            if (filePath != null) {
                catchImagePo.addImage(new ImageDetailPo(filePath, url));
            }
        }
        return catchImagePo;
    }

    private String downloadFile(String url) {
        try {
            String filePath = HttpRequest.download(url, filepath);
            return filePath;
        } catch (Exception e) {
            return null;
        }
    }
}
