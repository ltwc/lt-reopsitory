package com.web.oa.service;

import com.web.oa.pojo.Employee;

public interface EmployeeService {

	public Employee findEmployeeByUserName(String username);
	
	public Employee findEmpById(long id);
	
}
