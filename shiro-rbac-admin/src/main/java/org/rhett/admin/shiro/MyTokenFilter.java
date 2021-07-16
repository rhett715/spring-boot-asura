package org.rhett.admin.shiro;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.rhett.admin.model.constant.SysConstant;
import org.rhett.admin.model.enumeration.ResultCode;
import org.rhett.admin.model.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Rhett
 * @Date 2021/6/24
 * @Description
 * 自定义jwt过滤器，对token进行处理
 */
public class MyTokenFilter extends BasicHttpAuthenticationFilter {

    /**
     * 判断用户是否想要登入
     * 只需检测Header里面是否有Authorization字段即可
     * @param request 请求
     * @param response 响应
     * @return boolean
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(SysConstant.TOKEN_HEAD);
        return authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        //return super.executeLogin(request, response);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String header = httpServletRequest.getHeader(SysConstant.TOKEN_HEAD);
        MyAuthenticationToken token = new MyAuthenticationToken(header);
        // 提交给realm进行登入
        getSubject(request, response).login(token);
        return true;
    }

    /**
     * 判断是否允许通过
     * 最终返回的结果都是true，即允许访问
     * 如果返回false，请求会被拦截
     * 所以这里返回true，Controller中可以通过subject.isAuthenticated()来判断用户是否登录
     * 如果有些资源只有登录用户才能访问，只需要在方法上面加上@RequiresAuthentication注解即可
     * 但有一个缺点，就是不能对GET，POST等请求进行分别过滤鉴权，因为重写了官方的方法
     * @param request 请求
     * @param response 响应
     * @param mappedValue
     * @return boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
                return true;
            } catch (Exception e) {
                responseError(response, "shiro fail");
            }
        }
        return false;
    }

    /**
     * 重写 onAccessDenied 方法，避免父类中调用再次executeLogin
     * @param request 请求
     * @param response 响应
     * @param mappedValue
     * @return boolean
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) {
        this.sendChallenge(request, response);
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Allow-Headers"));
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    private void responseError(ServletResponse response, String msg) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setCharacterEncoding("UTF-8");
        try {
            httpResponse.getWriter().append(Result.failure(ResultCode.COMMON_ERROR, msg).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
