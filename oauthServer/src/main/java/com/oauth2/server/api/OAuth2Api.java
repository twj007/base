package com.oauth2.server.api;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/31
 **/
@RestController
public class OAuth2Api {

    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String secret;

    private Map<String, String> authCode = new HashMap();
    private String redirect_uri = "http://localhost:8083/accessToken";
    private Map<String, String> token = new HashMap<>();

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/toOAuth2")
    public ModelAndView toOAuth2(){
        //检查授权

        //通过jwt获取基本信息

        //跳转授权页面
        ModelAndView mav = new ModelAndView("/toOAuth2");
        return mav;

    }


    /***
     *  返回授权码
     */
    @RequestMapping("/oauth2")
    public ResponseEntity oauth2(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException, Exception {
        try {
            //构建OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            //检查传入的客户端id是否正确
            if (!clientId.equals(oauthRequest.getClientId())) {
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription("INVALID_CLIENT_DESCRIPTION")
                        .buildJSONMessage();
                return new ResponseEntity(
                        response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }
            // 加密转发请求到对应的服务端，验证通过后将会返回code
            String username = "";
            //生成授权码
            String authorizationCode = null;
            //responseType目前仅支持CODE，另外还有TOKEN
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oauthIssuerImpl.authorizationCode();
                redisTemplate.opsForValue().set("oauth:"+authorizationCode, username);
                authCode.put(authorizationCode, username);
            }
            //进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request,
                            HttpServletResponse.SC_FOUND);
            //设置授权码
            builder.setCode(authorizationCode);
            //得到到客户端重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            //String redirectURI = redirect_uri;
            //构建响应
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            //根据OAuthResponse返回ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            //出错处理
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                //告诉客户端没有传入redirectUri直接报错
                return new ResponseEntity(
                        "OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
            }
            //返回错误消息（如?error=）
            final OAuthResponse response =
                    OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                            .error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        }
    }

    /***
     * 在生成code之后传递过来验证并颁发token
     * @return
     */
    @RequestMapping("/accessToken")
    public ResponseEntity accessToken(HttpServletRequest request) throws Exception{
        try {
            //构建OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

            //检查提交的客户端id是否正确
            if (!clientId.equals(oauthRequest.getClientId())) {
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription("INVALID_CLIENT_DESCRIPTION")
                        .buildJSONMessage();
                return new ResponseEntity(
                        response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            // 检查客户端安全KEY是否正确
            if (!secret.equals(oauthRequest.getClientSecret())) {
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                        .setErrorDescription("INVALID_CLIENT_DESCRIPTION")
                        .buildJSONMessage();
                return new ResponseEntity(
                        response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            String code = oauthRequest.getParam(OAuth.OAUTH_CODE);
            // 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
            if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(
                    GrantType.AUTHORIZATION_CODE.toString())) {
                if (authCode.get(code) == null) {
                    OAuthResponse response = OAuthASResponse
                            .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription("错误的授权码")
                            .buildJSONMessage();
                    return new ResponseEntity(
                            response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                }
            }

            //生成Access Token
            OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            final String accessToken = oauthIssuerImpl.accessToken();
            token.put(accessToken,
                    authCode.get(code));

            //生成OAuth响应
            OAuthResponse response = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn("360") // expires in  xxx second
                    .buildJSONMessage();

            //根据OAuthResponse生成ResponseEntity
            return new ResponseEntity(
                    response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            //构建错误响应
            OAuthResponse res = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
                    .buildJSONMessage();
            return new ResponseEntity(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
        }
    }

    @RequestMapping("/getUserInfo")
    public ResponseEntity getUserInfo(HttpServletRequest request) throws OAuthSystemException {
        try {
            OAuthAccessResourceRequest oauthRequest =
                    new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            //获取Access Token
            String accessToken = oauthRequest.getAccessToken();

            //验证Access Token
            if (token.get(accessToken) == null) {
                // 如果不存在/过期了，返回未验证错误，需重新验证
                OAuthResponse oauthResponse = OAuthRSResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm("RESOURCE_SERVER_NAME")
                        .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                        .buildHeaderMessage();

                HttpHeaders headers = new HttpHeaders();
                headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,
                        oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }
            //返回用户名
            String username = token.get(accessToken);
            return new ResponseEntity(username, HttpStatus.OK);
        } catch (OAuthProblemException e) {
            //检查是否设置了错误码
            String errorCode = e.getError();
            if (OAuthUtils.isEmpty(errorCode)) {
                OAuthResponse oauthResponse = OAuthRSResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm("RESOURCE_SERVER_NAME")
                        .buildHeaderMessage();

                HttpHeaders headers = new HttpHeaders();
                headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,
                        oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
            }
            OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm("RESOURCE_SERVER_NAME")
                    .setError(e.getError())
                    .setErrorDescription(e.getDescription())
                    .setErrorUri(e.getUri())
                    .buildHeaderMessage();

            HttpHeaders headers = new HttpHeaders();
            headers.add(OAuth.HeaderType.WWW_AUTHENTICATE,
                    oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
