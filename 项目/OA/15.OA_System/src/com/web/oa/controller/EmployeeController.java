package com.web.oa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.oa.pojo.Employee;
import com.web.oa.service.EmployeeService;
import com.web.oa.utils.Constants;


@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	/*@RequestMapping("/login")
	public String login(String username,String password,HttpSession session,Model model){
		
		Employee loginUser = employeeService.findEmployeeByUserName(username);
		
		if(loginUser != null){
			//判断密码
			if(loginUser.getPassword().equals(password)){//登录成功
				session.setAttribute(Constants.GLOBAL_SESSION_ID, loginUser);
				return "index";
			}else{
				model.addAttribute("errorMsg", "账号或密码错误");
				return "login";
			}
		}else{
			model.addAttribute("errorMsg", "账号或密码错误");
			return "login";
		}
		
	}*/
	
	
	
	//不是真正的认证逻辑代码，应该在reaml中实现，是一个处理报错提示的代码
	@RequestMapping("/login")
	public String login(HttpServletRequest request,Model model){
		
		
		//获取报错的提示信息
		String errorException = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(errorException != null){
			if(UnknownAccountException.class.getName().equals(errorException)){
				
				System.out.println("账号错误");
				model.addAttribute("errorMsg", "账号错误");
				return "login";
				
			}
			if(IncorrectCredentialsException.class.getName().equals(errorException)){
				System.out.println("密码错误");
				model.addAttribute("errorMsg", "密码错误");
				return "login";
				
			}
			
			
			if("invalidateCodeError".equals(errorException)){
				System.out.println("验证码错误");
				model.addAttribute("errorMsg", "验证码错误");
				return "login";
			}
			
			
		}
		
		//返回登录页
		return "login";
		
		
	}
	
/*	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "login";
		
	}*/
	
	
	
}
