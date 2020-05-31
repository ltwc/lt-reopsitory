package com.web.oa.service;

import java.util.List;

import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Employee;
import com.web.oa.pojo.SysPermission;
import com.web.oa.pojo.SysRole;
import com.web.oa.pojo.TreeMenu;



public interface SysService {
	
	//根据用户的身份和密码 进行认证，如果认证通过，返回用户身份信息
	//public ActiveUser authenticat(String userName,String password) throws Exception;
	
	//根据用户账号查询用户信息
	public Employee findSysEmployeeByUserName(String username)throws Exception;
	
	//根据用户名查询权限范围的菜单
	public List<SysPermission> findMenuListByUserName(String name) throws Exception;
	
	//根据用户名查询权限范围的url
	public List<SysPermission> findPermissionListByUserName(String name) throws Exception;

	//查询菜单
	public List<TreeMenu> findMenuList();
	
	//查找所有的角色
	public List<SysRole> findAllRole();

	//添加角色和对应的权限
	public void addRole(String rolename, String[] menunid, String[] subMenuid);

	//添加权限
	public void addSysPermission(SysPermission sysPermission);

	//查询所有菜单及权限
	public List<TreeMenu> findAllMenuList();

	public List<SysPermission> findRolePermissionListByRoleId(String id);

	//删除角色
	public void deleteRole(String id);

	void updateUserAndRole(Employee employee);
	void saveEditorRolePermission(String roleId,Integer[] permissionIds);


	
	
}
