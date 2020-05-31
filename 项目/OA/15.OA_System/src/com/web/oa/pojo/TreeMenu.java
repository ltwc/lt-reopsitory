package com.web.oa.pojo;

import java.util.List;

//菜单的封装类

public class TreeMenu implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private String name;//一级菜单的名称
	
	private List<SysPermission> children;//二级菜单的数据

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SysPermission> getChildren() {
		return children;
	}

	public void setChildren(List<SysPermission> children) {
		this.children = children;
	}
	
	
	
	
}
