package com.web.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.oa.mapper.EmployeeCustomMapper;
import com.web.oa.mapper.EmployeeMapper;
import com.web.oa.mapper.SysPermissionMapperCustom;
import com.web.oa.pojo.Employee;
import com.web.oa.pojo.EmployeeCustom;
import com.web.oa.pojo.EmployeeRole;
import com.web.oa.pojo.SysPermission;
import com.web.oa.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private EmployeeCustomMapper employeeCustomMapper;
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	
	@Override
	public List<EmployeeCustom> findAllEmployeeCustomList() {
		return employeeCustomMapper.findAllEmployeeCustom();
	}


	@Override
	public int updateEmployeeRoleByUserId(Long id,Integer role) {
		Employee emp = employeeMapper.selectByPrimaryKey(id);
		emp.setRole(role);
		return employeeMapper.updateByPrimaryKey(emp);
		
	}

	
	//查看用户的角色及对应的权限
	@Override
	public EmployeeRole  selectSysPermission(String name) throws Exception {
		String rolename = employeeCustomMapper.findEmployeeRole(name);
		
		List<SysPermission> permissionList = sysPermissionMapperCustom.findPermissionListByUserName(name);
		
		EmployeeRole employeeRole = new EmployeeRole();
		employeeRole.setName(rolename);
		employeeRole.setPermissionList(permissionList);
		
//		System.out.println("角色名称："+employeeRole.getName());
		return employeeRole;
	}

	
}
