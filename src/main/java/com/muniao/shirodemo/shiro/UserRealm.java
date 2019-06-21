package com.muniao.shirodemo.shiro;

import com.muniao.shirodemo.domain.User;
import com.muniao.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义realm
 *
 * @author jianping.lu
 */
public class UserRealm extends AuthorizingRealm
{

    @Autowired
    private UserService userService;

    //执行授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0)
    {
        System.out.println("执行授权");
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //给资源授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission(user.getPerms());
        return simpleAuthorizationInfo;
    }

    //执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException
    {
        System.out.println("执行认证逻辑");

        //shiro判断逻辑
        UsernamePasswordToken user = (UsernamePasswordToken) arg0;
        User realUser = new User();
        realUser.setName(user.getUsername());
        realUser.setPassword(String.copyValueOf(user.getPassword()));
        User newUser = userService.findUser(realUser);
        if (newUser == null)
        {
            //用户名错误
            //shiro会抛出UnknownAccountException异常
            return null;
        }

        return new SimpleAuthenticationInfo(newUser, newUser.getPassword(), "");
    }


}

