package com.framework.web.controller;

import com.framework.web.pojo.TokenUser;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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

    private String accessTokenURL = "http://localhost:8082/accessToken";

    private String userInfoUrl = "http://localhost:8082/getUserInfo";

    private String redirectUrl = "http:localhost:8083/test";

    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String secret;

    /***
     * 每次请求的请求头都会加上token， 通过拦截器对token是否过期等需要校验，如果过期，要做续签操作(oauth服务端做)
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/accessToken")
    public ResponseEntity accessToken(HttpServletRequest request) throws Exception{
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthClientRequest accessTokenRequest = OAuthClientRequest
                .tokenLocation(accessTokenURL) //获取token的url
                .setGrantType(GrantType.AUTHORIZATION_CODE) //授权类型是授权码
                .setClientId(clientId).setClientSecret(secret) // client id 以及密钥
                .setCode(request.getParameter("code"))
                .setRedirectURI(redirectUrl) // 授权码以及重定向的url
                .buildQueryMessage();
        //获取access token
        accessTokenRequest.addHeader("Accept", "application/json");
        accessTokenRequest.addHeader("Content-Type", "application/json");

        OAuthAccessTokenResponse oAuthResponse =
                oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();
        //获取user info
        OAuthClientRequest userInfoRequest =
                new OAuthBearerClientRequest(userInfoUrl)
                        .setAccessToken(accessToken).buildQueryMessage();
        OAuthResourceResponse resourceResponse = oAuthClient.resource(
                userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        String username = resourceResponse.getBody();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, accessToken.toCharArray());
        subject.login(token);
        return ResponseEntity.ok(accessToken+":"+username);
    }
}
