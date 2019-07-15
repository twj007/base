package com.shiro.component;

import com.shiro.model.ShiroUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.tomcat.util.buf.HexUtils;

import java.util.HashSet;
import java.util.Set;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/15
 **/
public class MyRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if(principalCollection == null){
            throw new AuthorizationException("user principal is null");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(principalCollection.asSet());
        info.setRoles(((ShiroUser)principalCollection.getPrimaryPrincipal()).getRoles());
        return info;
    }

    /****
     * 获取数据库中的用户名密码比对
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println(authenticationToken.getPrincipal());
        System.out.println((authenticationToken.getCredentials()).hashCode());
        String password = new String((char[])authenticationToken.getCredentials());
        System.out.println(password.hashCode());

        if("jien007".equals((authenticationToken.getPrincipal())) && "12345".equals(password)){
            ShiroUser user = new ShiroUser();
            Set<String> premissions = new HashSet<>();
            premissions.add("customer:search");
            premissions.add("admin:save");
            premissions.add("admin:update");
            Set<String> roles = new HashSet<>();
            roles.add("admin");
            roles.add("user");
            user.setUsername((String)authenticationToken.getPrincipal());
            user.setPassword(new String((char[])authenticationToken.getCredentials()));
            user.setPerms(premissions);
            user.setRoles(roles);
            return new SimpleAuthenticationInfo(user, user.getPassword(), user.getUsername());
        }
        return null;
    }
}
