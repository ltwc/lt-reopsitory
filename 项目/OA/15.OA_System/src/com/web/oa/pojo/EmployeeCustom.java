package com.web.oa.pojo;

//员工扩展类
public class EmployeeCustom extends Employee {
	
	private Integer roleId;
	
	private String rolename;
	private String manager;
	
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
	
	
}
