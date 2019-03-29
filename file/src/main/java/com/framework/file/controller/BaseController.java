package com.framework.file.controller;

import com.framework.file.pojo.user.User;
import com.framework.file.pojo.user.UserDetail;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView toError(HttpServletRequest request){
        Integer resCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(resCode == 401 || resCode == 403 || resCode == 404){
            return new ModelAndView("400");
        }else{
            return new ModelAndView("500");
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/")
    public ModelAndView index(HttpServletRequest request, ModelAndView mav){
        mav = new ModelAndView();
        UserDetail user = (UserDetail) request.getSession().getAttribute("user");
        if( user == null){
            mav.setViewName("/user/login");
        }else{
            mav.setViewName("index");
            mav.addObject(user);
        }
        return mav;
    }
}
