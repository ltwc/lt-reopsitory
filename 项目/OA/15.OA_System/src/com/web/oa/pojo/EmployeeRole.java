package com.web.oa.pojo;

import java.util.List;


//角色权限表
public class EmployeeRole {

	private String  name;//角色
	
	private List<SysPermission> permissionList;// 权限

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SysPermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<SysPermission> permissionList) {
		this.permissionList = permissionList;
	}

	
	
	
	
	
}
