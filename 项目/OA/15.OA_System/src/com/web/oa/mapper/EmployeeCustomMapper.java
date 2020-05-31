package com.web.oa.mapper;

import java.util.List;

import com.web.oa.pojo.EmployeeCustom;
import com.web.oa.pojo.SysRole;

public interface EmployeeCustomMapper {
    
	public List<EmployeeCustom> findAllEmployeeCustom();
	
	public String findEmployeeRole(String username);
	
	
	
}