package com.web.gec.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String verif = (String) servletRequest.getAttribute("verif");
        String vrifyCode = (String) servletRequest.getSession().getAttribute("vrifyCode");
       if (vrifyCode!=null && verif!=null &&!vrifyCode.equals(verif)){
           request.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,"ErrorValideCode");
           return true; //返回到dologin
       }
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.getAndClearSavedRequest(request);
        WebUtils.redirectToSavedRequest(request,response,"/queryAuctions");
        return false;
    }
}
