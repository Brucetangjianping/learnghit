package com.jami.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by felixzhao on 15/1/7.
 */
public class EmojiFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        EmojiRequestWrapper wrapper = new EmojiRequestWrapper(req);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
