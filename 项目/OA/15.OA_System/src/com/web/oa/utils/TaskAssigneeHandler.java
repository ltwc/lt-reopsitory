package com.web.oa.utils;

import javax.servlet.http.HttpSession;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Employee;
import com.web.oa.service.EmployeeService;


public class TaskAssigneeHandler implements TaskListener {

	@Override
	public void notify(DelegateTask task) {
		
		
		//1.硬编码获取spring的容器
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		
		
		//原始方法 ：不加shiro
		//2.获取session
		//ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();	
		//HttpSession session = requestAttributes.getRequest().getSession();
		//Employee employee = (Employee) session.getAttribute(Constants.GLOBAL_SESSION_ID);

		
		//从新查询当前用户，再获取当前用户对应的领导 shiro
		ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();
		
		EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");

		//原始方法 ：不加shiro
		//Employee manager = employeeService.findEmpById(employee.getManagerId());
		
		//shiro
		Employee manager = employeeService.findEmpById(activeUser.getManagerId());
		
		//3.设置待办人
		task.setAssignee(manager.getName());
		
		//task.setAssignee("mike");
		
	}
	

}
