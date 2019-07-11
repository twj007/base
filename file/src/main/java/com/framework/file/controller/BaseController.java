package com.framework.file.controller;

import com.framework.file.pojo.user.Menu;
import com.framework.file.pojo.user.UserDetail;
import com.framework.file.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BaseController {

    @Autowired
    private UserService userService;

//    @RequestMapping("/error")
//    public ModelAndView toError(HttpServletRequest request){
//        Integer resCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if(resCode == 401 || resCode == 403 || resCode == 404){
//            return new ModelAndView("400");
//        }else{
//            return new ModelAndView("500");
//        }
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }

    @GetMapping("/")
    public ModelAndView index(HttpServletRequest request, ModelAndView mav){
        mav = new ModelAndView();
        UserDetail user = getUser();
        if( user == null){
            mav.setViewName("/user/login");
        }else{
            List<Menu> menus = userService.getMenu(user.getUser());
            mav.setViewName("index");
            mav.addObject("user", user.getUser());
            mav.addObject("menus", menus);
        }
        return mav;
    }

    protected UserDetail getUser(){
         Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
         if("anonymousUser".equals(auth.getPrincipal())){
            return null;
         }else {
             UserDetail userDetail = (UserDetail) auth.getPrincipal();
             return userDetail;
         }
    }
}
