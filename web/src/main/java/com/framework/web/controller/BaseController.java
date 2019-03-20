package com.framework.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok("ok");
    }
}
