package com.youzijie.util.StreamHandler;

import com.jami.util.Log;
import com.youzijie.util.HttpStreamHandler;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lei on 2015/9/22.
 */
public class GetHandler implements HttpStreamHandler {
    private static final String CHARSET = "UTF-8";
    @Override
    public String handle(InputStream istream) throws IOException {
        ByteArrayOutputStream bos = null;
        try {
            byte[] buf = new byte[1048576];
            bos = new ByteArrayOutputStream();
            int length = -1;
            while ((length = istream.read(buf)) >= 0) {
                bos.write(buf, 0, length);
            }
            buf = bos.toByteArray();
            String strDoc = new String(buf, CHARSET);
            // Log.run.info("Try to read file " + strDoc);
            return strDoc;
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
    }
}
