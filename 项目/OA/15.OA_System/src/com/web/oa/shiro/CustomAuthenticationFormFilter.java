package com.web.oa.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

//验证码处理
public class CustomAuthenticationFormFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 1.获取图片中的验证码
		String validateCode = (String) req.getSession().getAttribute("validateCode");
		// 2.获取输入的验证码
		String randomcode = req.getParameter("randomcode");

		// 验证码错误
		if (validateCode != null && randomcode != null && !validateCode.equals(randomcode)) {
			// 把错误的消息保存到作用域中
			req.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "invalidateCodeError");
			return true;
		}

		// 执行默认的操作，调用Realm
		return super.onAccessDenied(request, response);
	}

	//https://blog.csdn.net/fendou_0123456789/article/details/81566882?utm_source=blogxgwz0
	//shiro 登录成功后 不跳转到 successUrl 的问题解决--> 重写 FormAuthenticationFilter 父类的  issueSuccessRedirect 方法
	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
	}
	
	
	
	
//
	//或者   登录成功后 不跳转到 successUrl 的问题解决方式二
	  @Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
									 ServletResponse response) throws Exception {
		WebUtils.getAndClearSavedRequest(request);
		WebUtils.redirectToSavedRequest(request, response, "/first");
		return false;
	}
	

}
