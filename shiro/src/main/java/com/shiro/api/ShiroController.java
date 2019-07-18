package com.shiro.api;

import com.shiro.model.ShiroUser;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/15
 **/
@RestController
public class ShiroController {

//    @Autowired
//    RestTemplate restTemplate;


//    @RequestMapping("/v2")
//    public ResponseEntity v2(HttpServletRequest request){
//        System.out.println(request.getSession());
//        HttpHeaders head = new HttpHeaders();
//        head.add("Cookie", request.getSession().getId());
//        HttpEntity requestEntity = new HttpEntity(null, head);
//        ResponseEntity responseEntity = restTemplate.exchange("http://localhost:8082/v2", HttpMethod.POST, requestEntity, String.class);
//        return ResponseEntity.ok(responseEntity.getBody());
//    }

    @Autowired
    private RedisTemplate redisTemplate;

    /****
     * session共享示例。 在rest请求中通过请求头传递session，另一端通过这个请求头去redis中拿session的信息
     * 真实中可以通过拦截器去查redis里面是否有这个session，有就允许访问，没有就禁止访问
     * @param request
     * @return
     */
    @RequestMapping("/v2")
    @RequiresGuest
    public List v2(HttpServletRequest request){
        System.out.println(request.getHeader("Cookie"));
        String sessionId = request.getHeader("Cookie");
        List values = redisTemplate.opsForHash().values("spring:session:sessions:"+sessionId);
        return values;
    }

    /***
     * admin 权限访问
     * @param request
     * @return
     */
    @RequestMapping("/getInfo")
    @RequiresRoles("admin")
    public ResponseEntity getInfo(HttpServletRequest request){
        Enumeration<String> attrs = request.getSession().getAttributeNames();
        while(attrs.hasMoreElements()){
            String s = attrs.nextElement();
            System.out.println(s);
            System.out.println(request.getSession().getValue(s));
        }

        return ResponseEntity.ok("admin get info");
    }

    /***
     * 游客可访问 --> subject.getPrincipal == null
     */
    @RequestMapping("/v1")
    @RequiresGuest
    public String v1(){
        return "you are kick out";
    }



    // 已登录用户才能访问，这个注解比@RequiresUser更严格
    // 如果用户未登录调用该接口，会抛出UnauthenticatedException
    @RequiresAuthentication
    @GetMapping("/authn")
    public String authn() {
        return "@RequiresAuthentication";
    }

    // 已登录用户或“记住我”的用户可以访问
    // 如果用户未登录或不是“记住我”的用户调用该接口，UnauthenticatedException
    @RequiresUser
    @GetMapping("/user")
    public String user() {
        return "@RequiresUser";
    }

    // 要求登录的用户具有mvn:build权限才能访问
    // 由于UserService模拟返回的用户信息中有该权限，所以这个接口可以访问
    // 如果没有登录，UnauthenticatedException
    @RequiresPermissions("mvn:install")
    @GetMapping("/mvnInstall")
    public String mvnInstall() {
        return "mvn:install";
    }

    // 要求登录的用户具有mvn:build权限才能访问
    // 由于UserService模拟返回的用户信息中【没有】该权限，所以这个接口【不可以】访问
    // 如果没有登录，UnauthenticatedException
    // 如果登录了，但是没有这个权限，会报错UnauthorizedException
    @RequiresPermissions("gradleBuild")
    @GetMapping("/gradleBuild")
    public String gradleBuild() {
        return "gradleBuild";
    }

    // 要求登录的用户具有js角色才能访问
    // 由于UserService模拟返回的用户信息中有该角色，所以这个接口可访问
    // 如果没有登录，UnauthenticatedException
    @RequiresRoles("docker")
    @GetMapping("/docker")
    public String docker() {
        return "docker programmer";
    }

    // 要求登录的用户具有js角色才能访问
    // 由于UserService模拟返回的用户信息中有该角色，所以这个接口可访问
    // 如果没有登录，UnauthenticatedException
    // 如果登录了，但是没有该角色，会抛出UnauthorizedException
    @RequiresRoles("python")
    @GetMapping("/python")
    public String python() {
        return "python programmer";
    }

    /****
     * 这边再登陆完成后需要再次请求一次才会把另一个请求顶掉（前端强制成功后跳转）
     * @param user
     * @param request
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping("/login")
    public ResponseEntity login(ShiroUser user, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        if(StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())){
            return ResponseEntity.badRequest().body("username or password should not be null");
        }
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return ResponseEntity.ok("已登录");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        if(user.getRememberMe()){
            token.setRememberMe(true);
        }
        subject.login(token);
        request.getSession().setAttribute("username", user.getUsername());
        response.addHeader("refresh", "3;url='/success'");
        return ResponseEntity.ok("login success, you'll be redirect to homepage in 3sec");
    }

    @RequestMapping("/success")
    public void success(){

    }

    @RequestMapping("/logout")
    public ResponseEntity logout(){
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok("logout success");
    }

    @RequestMapping("/toLogin")
    public ResponseEntity toLogin(){
        return ResponseEntity.ok("请登陆");
    }

    @RequestMapping("/unAuthorized")
    public ResponseEntity unAnthorized(){
        return ResponseEntity.ok("没有权限");
    }

    /***
     * 授权
     * @return
     */
//    @RequestMapping("/runnAs")
//    public ResponseEntity runAs(){
//
//    }
//
    /***
     * 切换
     */
//    @RequestMapping("/switch")
//    public ResponseEntity switchRole(){
//
//    }



    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String secret;

    /***
     * 集成oauth2 登陆github
     */
    @RequestMapping("/oauth2")
    public ResponseEntity oauth2(HttpServletRequest request){
        try {

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation("https://github.com/login/oauth/access_token")
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(secret)
                    .setRedirectURI("/getInfo")
                    .buildQueryMessage();

            Object oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST, OAuthAccessTokenResponse.class);

//            String accessToken = oAuthResponse.getAccessToken();
//            Long expiresIn = oAuthResponse.getExpiresIn();
//
//            OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest("https://api.github.com/user")
//                    .setAccessToken(accessToken).buildQueryMessage();
//
//            OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
//            String username = resourceResponse.getBody();
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
