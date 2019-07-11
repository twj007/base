package com.framework.file.component;

import com.framework.file.pojo.user.UserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by macro on 2018/8/6.
 */
public class GoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
        //request.getSession().setAttribute("user", (UserDetail)authentication.getPrincipal());
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().print("{\"retCode\":200,\"message\":\"登录成功\"}");
        response.getWriter().flush();
    }
}