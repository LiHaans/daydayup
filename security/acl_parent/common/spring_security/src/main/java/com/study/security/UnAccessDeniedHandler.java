package com.golaxy.security;

import com.golaxy.utils.R;
import com.golaxy.utils.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: lihang
 * @Date: 2021-07-08 18:03
 * @Description:
 */
public class UnAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtil.out(httpServletResponse, R.error().message("没有权限"));
    }
}
