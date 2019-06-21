package com.muniao.shirodemo.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置类
 *
 * @author jianping.lu
 */
@Configuration
public class ShiroConfig
{

    //创建Realm
    @Bean(name = "userRealm")
    public UserRealm getUserRealm()
    {
        return new UserRealm();
    }

    //创建DefaultWebSecurityManager
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm)
    {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;

    }

    //创建ShiroFilterFactoryBean
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager)
    {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro内置过滤器
        /*
         * anon:表示可以匿名使用。
           authc:表示需要认证(登录)才能使用，没有参数
           roles：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
           perms：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
           rest：根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
           port：当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
           authcBasic：没有参数表示httpBasic认证
           ssl:表示安全的url请求，协议为https
           user:当登入操作时不做检查
         */
        Map<String, String> fMap = new HashMap<>();

        // 配置不会被拦截的链接 顺序判断
        fMap.put("/static/**", "anon");

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        fMap.put("/logout", "logout");

        //拦截页面
        fMap.put("/what", "authc");
        fMap.put("/one", "authc");
        fMap.put("/two", "authc");

        //拦截未授权
        fMap.put("/one", "perms[user:one]");
        fMap.put("/two", "perms[user:two]");

        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //fMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(fMap);

        //被拦截返回登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        //授权拦截返回页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        return shiroFilterFactoryBean;

    }




}
