package com.youzijie.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lei on 2015/9/22.
 */
public interface HttpStreamHandler {
    public String handle(InputStream istream) throws IOException;
}
