package com.web.oa.pojo;

import java.util.Iterator;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 用户身份信息，存入session 由于tomcat将session会序列化在本地硬盘上，所以使用Serializable接口
 * 
 * @author Thinkpad
 * 
 */
public class ActiveUser implements java.io.Serializable {
	private long userid;//用户id（主键）
	private String username;// 用户名称
	
	private long ManagerId; //上级id

	private List<TreeMenu> menusList;// 所有菜单
	
	private List<SysPermission> menus;// 本用户菜单
	private List<SysPermission> permissions;// 权限

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public List<SysPermission> getMenus() {
		return menus;
	}

	public void setMenus(List<SysPermission> menus) {
		this.menus = menus;
	}

	public List<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}

	
	public List<TreeMenu> getMenusList() {
		return menusList;
	}

	public void setMenusList(List<TreeMenu> menusList) {
		this.menusList = menusList;
	}

	public long getManagerId() {
		return ManagerId;
	}

	public void setManagerId(long managerId) {
		ManagerId = managerId;
	}


	
}
