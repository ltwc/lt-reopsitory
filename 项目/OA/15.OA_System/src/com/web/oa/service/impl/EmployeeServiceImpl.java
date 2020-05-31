package com.web.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.oa.mapper.EmployeeMapper;
import com.web.oa.pojo.Employee;
import com.web.oa.pojo.EmployeeExample;
import com.web.oa.service.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Override  //前提条件：用户名是唯一的
	public Employee findEmployeeByUserName(String username) {
		
		EmployeeExample example = new EmployeeExample();
		
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(username);
		
		List<Employee> list = employeeMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public Employee findEmpById(long id) {
		return employeeMapper.selectByPrimaryKey(id);
	}

}
