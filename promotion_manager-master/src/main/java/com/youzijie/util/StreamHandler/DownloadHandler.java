package com.youzijie.util.StreamHandler;

import com.youzijie.util.HttpStreamHandler;

import java.io.*;

/**
 * Created by lei on 2015/9/22.
 */
public class DownloadHandler implements HttpStreamHandler {
    private static final int BUFFER_SIZE = 8192;
    private String filePath;

    public DownloadHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String handle(InputStream istream) throws IOException {
        BufferedOutputStream bos = null;
        try {
            String filename = System.currentTimeMillis() + ".jpg";
            File dest = new File(filePath + filename);
            byte[] dataBuf = new byte[2048];

            BufferedInputStream bis = new BufferedInputStream(istream, BUFFER_SIZE);

            bos = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            return filename;
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
    }
}
