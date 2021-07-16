package org.rhett.admin.config;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.rhett.admin.shiro.CustomRealm;
import org.rhett.admin.shiro.MyTokenFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 * Shiro配置
 */
@Configuration
public class MyShiroConfig {

    /**
     * 代理生成器
     * 需要借助Spring AOP来扫描@RequiresRoles和@RequiresPermissions等注解。
     * 生成代理类实现功能增强，从而实现权限控制。需要配合AuthorizationAttributeSourceAdvisor一起使用，否则权限注解无效
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        //强制使用从cglib动态代理机制，防止重复代理可能引起代理出错问题
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 代理生成器相当于切面，此类相当于切点
     * @param securityManager 管理器
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 将自己的验证方式加入容器
     * @return CustomRealm
     */
    @Bean
    public CustomRealm myCustomRealm() {
        return new CustomRealm();
    }

    /**
     * 权限管理，配置Realm的管理认证
     * @return 安全管理器
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myCustomRealm());

        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        ThreadContext.bind(securityManager);
        return securityManager;
    }

    /**
     * Shiro过滤器工厂, 设置过滤和跳转条件
     * @param securityManager 安全管理器
     * @return ShiroFilterFactoryBean
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        //登陆页面，无权限时跳转的页面
        filterFactoryBean.setLoginUrl("/user/login");
        //自定义过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt", new MyTokenFilter());
        filterFactoryBean.setFilters(filterMap);

        //配置拦截规则，使用LinkedHashMap是为了保持顺序
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        //filterRuleMap.put("/**", "jwt");
        //首页配置放行
        filterRuleMap.put("/", "anon");
        //登录页面和登录请求路径放行
        filterRuleMap.put("/user/login", "anon");
        //filterMap.put("/do_login", "anon");
        //Swagger3.0文档
        filterRuleMap.put("/swagger-ui/*", "anon");
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");
        filterRuleMap.put("/v3/api-docs", "anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/kaptcha", "anon");
        //其他未配置的所有路径都需要验证，否则跳转至登录页
        filterRuleMap.put("/**", "jwt");
        filterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return filterFactoryBean;
    }
}
