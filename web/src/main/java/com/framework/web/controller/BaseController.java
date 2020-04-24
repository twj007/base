package com.framework.web.controller;

import com.framework.web.pojo.CodeModel;
import com.framework.web.pojo.TokenUser;
import com.framework.web.util.QRCodeUtil;
import org.apache.oltu.oauth2.client.HttpClient;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    private String accessTokenURL = "http://tu:8443/accessToken";

    private String userInfoUrl = "http://tu:8443/getUserInfo";

    private String redirectUrl = "http:tu:8443/test";

//    @Value("${oauth2.client.id}")
    private String clientId;

//    @Value("${oauth2.client.secret}")
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
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, accessToken.toCharArray());
//        subject.login(token);
        return ResponseEntity.ok(accessToken+":"+username);
    }


    @RequestMapping("/getUserInfo")
    public ResponseEntity getUserInfo(HttpServletRequest request) throws Exception{
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        String accessToken = request.getHeader("token");
        OAuthClientRequest userInfoRequest =
                new OAuthBearerClientRequest(userInfoUrl)
                        .setAccessToken(accessToken).buildQueryMessage();
        OAuthResourceResponse resourceResponse = oAuthClient.resource(
                userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        return ResponseEntity.ok(resourceResponse.getBody());
    }


    @RequestMapping("/testHead")
    public ResponseEntity testHead(HttpServletRequest request){
        String val = request.getHeader("SESSION");
        return ResponseEntity.ok(val);
    }


    @RequestMapping("/github")
    public void toGitHub(HttpServletResponse response) throws IOException{
        StringBuffer sb = new StringBuffer();
        sb.append("https://github.com/login/oauth/authorize?")
                .append("client_id="+clientId)
                .append("&scope=user,public_repo");

        response.sendRedirect(sb.toString());
    }

    @RequestMapping("/getGithubAccessToken")
    public ResponseEntity<String> getGithubAccessToken(HttpServletRequest request) throws Exception{
        String tokenUrl = "https://github.com/login/oauth/access_token";
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthClientRequest accessTokenRequest = OAuthClientRequest
                .tokenLocation(tokenUrl) //获取token的url
                .setClientId(clientId)
                .setClientSecret(secret) // client id 以及密钥
                .setCode(request.getParameter("code"))
                 // 授权码以及重定向的url
                .buildQueryMessage();
        //获取access token 可返回 query string, json, xml
        accessTokenRequest.addHeader("Accept", "application/json");
        accessTokenRequest.addHeader("Content-Type", "application/json");

        OAuthAccessTokenResponse oAuthResponse =
                oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();
        System.out.println(expiresIn);
        return ResponseEntity.ok(accessToken);
    }


    @RequestMapping("/testQRCode")
    public void testQRCode(HttpServletResponse response){
        String imgname = String.valueOf(System.currentTimeMillis()) + ".png";
        CodeModel info = new CodeModel();
        info.setContents("客户:倍特 品牌:倍特 型号:XH001 日期:2018-06-19 检验员:易工");
        info.setWidth(300);
        info.setHeight(300);
        info.setFontSize(16);
        //info.setLogoFile(new File("F:\\软件安全下载目录\\personnelManage\\" + imgname));
        info.setDesc("玫瑰之约");
        info.setDate("2018-06-19");
        info.setLogoFile(null);
        BufferedImage img = QRCodeUtil.createCodeImage(info);
        try {
            ImageIO.write(img, "png", response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // session失效
        request.getSession().invalidate();
        response.sendRedirect("https://tu:8443/cas/logout");
    }
    /**
     * 用户登出，并重定向回来
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout2")
    public void logout2(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // session失效
        request.getSession().invalidate();
        response.sendRedirect("http://tu:8443/cas/logout?service=http://tu:8080/logout/success");
    }
    /**
     * 登出成功回调
     * @return
     */
    @ResponseBody
    @RequestMapping("/logout/success")
    public String logoutPage(){
        return "登出成功，跳转登出页面";
    }


    @RequestMapping("/rest/user")
    public ResponseEntity userOption(HttpServletRequest request){

//        if(bindingResult.hasErrors()){
//            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
//        }
        String method = request.getMethod();
        if(HttpMethod.GET.matches(method)){
            // do something
            return ResponseEntity.ok("Get Request");
        }else if(HttpMethod.POST.matches(method)){
            //
            return ResponseEntity.ok("Post Request");
        }else if(HttpMethod.PUT.matches(method)){
            //
            return ResponseEntity.ok("Put Request");
        }else if(HttpMethod.DELETE.matches(method)){
            //
            return ResponseEntity.ok("Delete Request");
        }

        return ResponseEntity.notFound().build();

    }


}
