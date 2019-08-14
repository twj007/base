package com.oauth2.server.admin.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/***
 **@project: base
 **@description: manage oauth2 info
 **@Author: twj
 **@Date: 2019/07/31
 **/
@RestController
@RequestMapping("/admin")
public class OAuthAdminController {


    @RequestMapping("/OAuth2Config")
    public ModelAndView toOAuth2Config(){

        ModelAndView mav = new ModelAndView("/OAuth2");
        return mav;
    }

    @RequestMapping("/list")
    public ResponseEntity listOAuth2(){

        return ResponseEntity.ok(null);
    }

    @RequestMapping("/toAdd")
    public ModelAndView toAdd(){
        ModelAndView mav = new ModelAndView("/add");
        return mav;
    }

    @RequestMapping("/add")
    public ResponseEntity add(){

        return ResponseEntity.ok(null);
    }

    @RequestMapping("/disable")
    public ResponseEntity disable(){

        return ResponseEntity.ok(null);
    }


    @RequestMapping("/update")
    public ResponseEntity update(){

        return ResponseEntity.ok(null);
    }


}
