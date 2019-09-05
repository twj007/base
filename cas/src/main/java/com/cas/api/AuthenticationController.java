package com.cas.api;

import com.cas.dao.UserRepository;
import com.cas.dto.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/05
 **/
@RestController
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {

    @Autowired
    private UserRepository repository;


    @PostMapping("/login")
    public Object login(@RequestHeader HttpHeaders httpHeaders) {
        log.info("Rest api login.");
        log.debug("request headers: " + httpHeaders);
        SysUser user = null;
        try {
            UserTemp userTemp = obtainUserFormHeader(httpHeaders);

            //当没有 传递 参数的情况
            if(userTemp == null){
                return new ResponseEntity<SysUser>(HttpStatus.NOT_FOUND);
            }

            //尝试查找用户库是否存在
            user = repository.findByUsername(userTemp.username);
            if (user != null) {
                if (!user.getPassword().equals(userTemp.password)) {
                    //密码不匹配
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
                if (user.isDisabled()) {
                    //禁用 403
                    return new ResponseEntity(HttpStatus.FORBIDDEN);
                }
                if (user.isLocked()) {
                    //锁定 423
                    return new ResponseEntity(HttpStatus.LOCKED);
                }
                if (user.isExpired()) {
                    //过期 428
                    return new ResponseEntity(HttpStatus.PRECONDITION_REQUIRED);
                }
            } else {
                //不存在 404
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.info("[{" + user.getUsername() + "}] login is ok");
        //成功返回json
        return user;
    }


    /**
     * 根据请求头获取用户名及密码
     *
     * @param httpHeaders
     * @return
     * @throws UnsupportedEncodingException
     */
    private UserTemp obtainUserFormHeader(HttpHeaders httpHeaders) throws UnsupportedEncodingException {
        /**
         *
         * This allows the CAS server to reach to a remote REST endpoint via a POST for verification of credentials.
         * Credentials are passed via an Authorization header whose value is Basic XYZ where XYZ is a Base64 encoded version of the credentials.
         */
        //根据官方文档，当请求过来时，会通过把用户信息放在请求头authorization中，并且通过Basic认证方式加密
        String authorization = httpHeaders.getFirst("authorization");//将得到 Basic Base64(用户名:密码)
        if(StringUtils.isEmpty(authorization)){
            return null;
        }
        String baseCredentials = authorization.split(" ")[1];
        String usernamePassword = new String(Base64Utils.decodeFromString(baseCredentials), "UTF-8");//用户名:密码
        log.debug("login user: " + usernamePassword);
        String credentials[] = usernamePassword.split(":");
        return new UserTemp(credentials[0], credentials[1]);
    }


    /**
     * 解析请求过来的用户
     */
    private class UserTemp {
        private String username;
        private String password;

        public UserTemp(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
