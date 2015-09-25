package com.youzijie.ueditor.svc;

import com.jami.exception.BusinessException;
import com.youzijie.ueditor.Po.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by lei on 2015/7/16.
 */
public interface EditorService {
    public ConfigPo getConfig();
    public UploadImagePo uploadImage(MultipartFile upfile) throws BusinessException;
    public UploadScrawlPo uploadScrawl() throws BusinessException;
    public UploadVideoPo uploadVideo(MultipartFile upfile) throws BusinessException;
    public UploadFilePo uploadFile(MultipartFile upfile) throws BusinessException;
    public ListImagePo listImage(int start, int size);
    public CatchImagePo catchImage(List<String> source);
}