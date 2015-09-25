package com.youzijie.util;

import com.jami.util.Log;
import com.youzijie.util.StreamHandler.DownloadHandler;
import com.youzijie.util.StreamHandler.GetHandler;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

/**
 * Created by lei on 2015/7/20.
 */
public class HttpRequest {
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 10000;
    private static final String CHARSET = "UTF-8";
    private static final int BUFFER_SIZE = 8192;

    private static HttpURLConnection produceConnection(final String urlStr) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();

        huc.setConnectTimeout(CONNECTION_TIMEOUT);
        huc.setReadTimeout(READ_TIMEOUT);
        huc.connect();
        return huc;
    }

    public static String doRequest(final String urlStr, HttpStreamHandler handler) throws IOException {
        if (urlStr == null) {
            return null;
        }

        HttpURLConnection huc = null;
        try {
            URL url = new URL(urlStr);
            // Log.run.info("Request url " + url);
            huc = (HttpURLConnection) url.openConnection();

            huc.setConnectTimeout(CONNECTION_TIMEOUT);
            huc.setReadTimeout(READ_TIMEOUT);
            huc.connect();

            // [TODO(liulei)] Check MIMEType
            // String suffix = MIMEType.getSuffix( huc.getContentType() );

            InputStream is = huc.getInputStream();

            return handler.handle(is);
        } finally {
            if (huc != null) {
                huc.disconnect();
            }
        }
    }

    /**
     * 下载jpg文件
     * @param urlStr jpg文件的url
     * @param filePath 保存的文件路径
     * @return 保存后的jpg文件名
     * @throws IOException
     */
    public static String download(final String urlStr, final String filePath) throws IOException {
        return doRequest(urlStr, new DownloadHandler(filePath));
    }

    public static String doGet(final String urlStr) throws IOException {
        return doRequest(urlStr, new GetHandler());
    }

    public static String doPost(final String urlStr, String postData,
                                int connTimeout, int readTimeout) throws Exception {
        if (urlStr == null) {
            return null;
        }
        HttpURLConnection huc = null;

        try {
            URL url = new URL(urlStr);
            huc = (HttpURLConnection) url.openConnection();

            if (connTimeout > 0)
                huc.setConnectTimeout(connTimeout);
            else
                huc.setConnectTimeout(CONNECTION_TIMEOUT);

            if (readTimeout > 0)
                huc.setReadTimeout(readTimeout);
            else
                huc.setReadTimeout(READ_TIMEOUT);

            huc.setRequestMethod("POST");
            huc.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            huc.setDoInput(true);

            if (postData != null && postData.trim().length() > 0) {
                huc.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(
                        huc.getOutputStream(), CHARSET);
                wr.write(postData);
                wr.flush();
            }

            int rspCode = huc.getResponseCode();
            String result = "";
            if (rspCode == 200) {
                BufferedInputStream read = new BufferedInputStream(
                        huc.getInputStream());
                byte[] b = new byte[1024 * 100];
                int len = 0;
                while (len != -1) {
                    len = read.read(b);
                    if (len > 0) {
                        result += new String(b, 0, len, CHARSET);
                    }
                }
            }
            return result;
        } finally {
            if (huc != null) {
                huc.disconnect();
            }
        }
    }

    /** POST请求数据 */
    public static String doPost(final String urlStr, String postData)
            throws Exception {
        return doPost(urlStr, postData, CONNECTION_TIMEOUT, READ_TIMEOUT);
    }

    public static String upload(final String urlStr, String filename) throws Exception {
        if (urlStr == null) {
            return null;
        }
        String boundary = Long.toHexString(System.currentTimeMillis());
        File file = new File(filename);
        String CRLF = "\r\n";
        String charset = CHARSET;
        String param = "value";

        URLConnection connection = new URL(urlStr).openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try {
            OutputStream output = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
            // Send normal param.
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(param).append(CRLF).flush();

            // Send binary file.
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + file.getName() + "\"").append(CRLF);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(file.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

            // End of multipart/form-data.
            writer.append("--" + boundary + "--").append(CRLF).flush();

            int rspCode = ((HttpURLConnection) connection).getResponseCode();
            String result = "";
            if (rspCode == 200) {
                BufferedInputStream read = new BufferedInputStream(
                        connection.getInputStream());
                byte[] b = new byte[1024 * 100];
                int len = 0;
                while (len != -1) {
                    len = read.read(b);
                    if (len > 0) {
                        result += new String(b, 0, len, CHARSET);
                    }
                }
            }
            return result;
        } finally {
            if (connection != null) {
            }
        }

    }
}
