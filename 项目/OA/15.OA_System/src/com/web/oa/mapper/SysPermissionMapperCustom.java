package com.web.oa.mapper;

import java.util.List;

import com.web.oa.pojo.SysPermission;
import com.web.oa.pojo.SysRole;
import com.web.oa.pojo.TreeMenu;



public interface SysPermissionMapperCustom {
	
	//根据用户名查询菜单
	public List<SysPermission> findMenuListByUserName(String name)throws Exception;
	//根据用户名查询权限url
	public List<SysPermission> findPermissionListByUserName(String name)throws Exception;

	//查询菜单
	public List<TreeMenu> findMenuList();
	
	public List<SysPermission> getSubMenu();
	
	
	//查询所有菜单
    public List<TreeMenu> findAllMenuList();
	public List<SysPermission> getAllSubMenu();
	
	
	//查找所有的角色
	public List<SysRole> findAllSysRole();
	
	//查找角色的所有权限
	public List<SysPermission> findPermissionListByRoleId(String id);
	
	
}
