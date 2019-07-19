package com.framework.web.component;

import com.framework.web.pojo.TokenUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/17
 **/
public class PasswordRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if(principalCollection == null){
            throw new AuthorizationException("user principal is null");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(principalCollection.asSet());
        return info;
    }

//    public static void main(String[] args) {
//        DefaultPasswordService passwordService = new DefaultPasswordService();
//        String encrypted = passwordService.encryptPassword("123456");
//        System.out.println(passwordService.passwordsMatch("123456", encrypted));
//    }

    /***
     * 这里是通过自定义的matcher去校验数据， 通过在数据库中查出的数据去创建校验info去和token中的值做比较，比较不同则抛出自定义异常，并对异常数据处理
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        TokenUser tokenUser = new TokenUser();
        tokenUser.setUsername((String) authenticationToken.getPrincipal());
        tokenUser.setAccessToken(new String((char[])authenticationToken.getCredentials()));
        return new SimpleAuthenticationInfo(tokenUser.getUsername(), tokenUser.getAccessToken(), tokenUser.getUsername());
    }
}
