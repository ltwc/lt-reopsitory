package com.web.oa.service;

import java.util.List;

import com.web.oa.pojo.Employee;
import com.web.oa.pojo.EmployeeCustom;
import com.web.oa.pojo.EmployeeRole;
import com.web.oa.pojo.SysPermission;

public interface UserService {

	public List<EmployeeCustom> findAllEmployeeCustomList();
	
	public int updateEmployeeRoleByUserId(Long id,Integer role);

	public EmployeeRole  selectSysPermission(String name) throws Exception;

	
}
