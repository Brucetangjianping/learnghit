package com.youzijie.upload.controller;

import com.jami.common.JsonModelResult;
import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.jami.util.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lei on 2015/7/17.
 */

@Controller
@RequestMapping(value="/api/upload/")
public class UploadController {
    private static String filepath = "/data/jweb_static/jweb_promotion_manager/upload/";

    @RequestMapping(value="/pic")
    public ModelAndView upload(MultipartFile file,
                               @RequestParam(required = false, defaultValue = "")String scale)
        throws BusinessException {
        ModelAndView mv = new ModelAndView();
        JsonModelResult result = new JsonModelResult();

        String filename = System.currentTimeMillis() + ".jpg";
        File dest = new File(filepath + filename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            Log.run.error(e.getMessage());
            throw BusinessException.createInstance(BizErrorCode.UPLOAD_FAILED);
        }

        result.setData(filename);

        mv.addAllObjects(result.getModelMap());
        return mv;
    }

    private static void resizeWidth(String org, String dest, int height,
                                   int width) {
        // LogRecord.recode(ImageUtils.class, "resizeWidth", org);
        String pictype = "";
        if (!"".equals(org) && org != null) {
            pictype = org.substring(org.lastIndexOf(".") + 1, org.length());
        }
        double ratio = 0; // 缩放比例
        File o = new File(org);
        File d = new File(dest);
        BufferedImage bi;
        try {
            bi = ImageIO.read(o);
            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            int itempWidth = bi.getWidth();

            // 计算比例
            if (itempWidth != width) {
                ratio = ((new Integer(width)).doubleValue() / itempWidth);
                AffineTransformOp op = new AffineTransformOp(
                        AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
                ImageIO.write((BufferedImage) itemp, pictype, d);
            }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
