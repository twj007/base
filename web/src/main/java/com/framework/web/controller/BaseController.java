package com.framework.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BaseController {

    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/")
    public ModelAndView t(){
        return new ModelAndView("/4xx");
    }
}
