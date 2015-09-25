package com.jami.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by felixzhao on 15/1/7.
 */
public class EmojiRequestWrapper extends HttpServletRequestWrapper {

//    private HttpServletRequest request;
//
//
    public EmojiRequestWrapper(HttpServletRequest request) {
        super(request);
//        this.request = request;
    }
//
//    public String getQueryString(){
//        String queryString = request.getQueryString();
//        return EmojiParser.parseToHtml(queryString);
//    }
//
//    public String getParameter(String name) {
//        String value = request.getParameter(name);
//        return EmojiParser.parseToHtml(value);
//    }
//
//    public String[] getParameterValues(String name) {
//        return request.getParameterValues(name);
//    }
//
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        InputStream is = request.getInputStream();
//        BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
//        br.re
//        return new ServletInputStreamImpl(new ByteArrayInputStream(body));
//    }
//
//    @Override
//    public BufferedReader getReader() throws IOException {
//        String enc = getCharacterEncoding();
//        if(enc == null) enc = "UTF-8";
//        return new BufferedReader(new InputStreamReader(getInputStream(), enc));
//    }
//
//    public Map getParameterMap() {
//        Map map = new HashMap();
//        Enumeration enum = getParameterNames();
//        while (enum.hasMoreElements()) {
//            String name = (String) enum.nextElement();
//            map.put(name, mreq.getParameterValues(name));
//        }
//        return map;
//    }

}
