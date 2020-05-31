package com.web.oa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.oa.pojo.Employee;
import com.web.oa.pojo.EmployeeCustom;
import com.web.oa.pojo.EmployeeRole;
import com.web.oa.pojo.SysPermission;
import com.web.oa.pojo.SysRole;
import com.web.oa.pojo.TreeMenu;
import com.web.oa.service.SysService;
import com.web.oa.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SysService sysService;
	
	
	@RequestMapping("/findUserList")
	public ModelAndView findUserList() {
		
		//用户列表
		List<EmployeeCustom> employeelist = userService.findAllEmployeeCustomList();
		//角色列表
		List<SysRole> rolelist = sysService.findAllRole();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("userList", employeelist);
		mv.addObject("allRoles", rolelist);
		mv.setViewName("userlist");
		return mv;
	}
		
	    //修改用户角色
		@ResponseBody
		@RequestMapping("/assignRole")
		public Map<String, String> assignRole(Employee employee) {
//			System.out.println("修改后的角色id："+employee.getRole());
//			System.out.println("被修改的用户id："+employee.getId());
			
			int updateInfo = userService.updateEmployeeRoleByUserId(employee.getId(),employee.getRole());
			sysService.updateUserAndRole(employee);
			Map<String, String> map = new HashMap<>();
			if(updateInfo>0){
				map.put("msg", "更改角色成功！！！");
			}else{
				map.put("msg", "更改角色失败！！！");
			}
			
			return map;
		}
		
	
	@ResponseBody
	@RequestMapping("/viewPermissionByUser")
	public EmployeeRole viewPermissionByUser(Employee employee) throws Exception {
		System.out.println("查看权限的用户名："+employee.getName());
		
		
		EmployeeRole employeeRole = userService.selectSysPermission(employee.getName());
		
		return employeeRole;
	}
	
	
	@RequestMapping("/toAddRole")
	public ModelAndView toAddRole() {
		
		List<TreeMenu> menuList = sysService.findMenuList();
		
		for (TreeMenu treeMenu : menuList) {
			System.out.println(treeMenu.getName());
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("menusList", menuList);
		mv.setViewName("AddRole");
		return mv;
	}
	
	
	//添加角色
	@RequestMapping("/addRole")
	public String addRole(ModelMap model,String [] menunid,String[] subMenuid,String rolename) {
		
		System.out.println("角色名称："+rolename);
		if (menunid!=null&&menunid.length > 0){
			for (String i : menunid) {
				System.out.println("menunid:"+i);
			}
			System.out.println("----------------------------");
		}
		if (subMenuid!=null && subMenuid.length > 0){
			for (String i : subMenuid) {
				System.out.println("subMenuid"+i);
			}

		}
		//添加角色和角色对应的权限
		sysService.addRole(rolename,menunid,subMenuid);

		
		return "redirect:/findRoles";
	}
	
	
	@RequestMapping("/findRoles")
	public ModelAndView findRoles() {
		
		//角色集合
		List<SysRole> RoleList = sysService.findAllRole();
		
		//角色及权限集合
		List<TreeMenu> menuList = sysService.findAllMenuList();
		
		for (TreeMenu treeMenu : menuList) {
			List<SysPermission> children = treeMenu.getChildren();
			for (SysPermission sysPermission : children) {
				System.out.println("我是分部所有权限："+sysPermission.getName());
			}
		}
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("menusList", menuList);
		
		mv.addObject("RoleList", RoleList);
		
		mv.setViewName("RoleList");
		return mv;
	}
	

	//报存权限
	@RequestMapping("/savePermission")
	public String savePermission(SysPermission sysPermission) {
//
//		System.out.println("权限名称："+sysPermission.getName());
//		System.out.println("type:"+sysPermission.getType());
//		System.out.println("url"+sysPermission.getUrl());
//		System.out.println("Percode"+sysPermission.getPercode());
//		System.out.println("Parentid"+sysPermission.getParentid());
		
		sysService.addSysPermission(sysPermission);
		
		return "redirect:/toAddRole";
	}
	
	
	//编辑角色  
	@ResponseBody
	@RequestMapping("/editorRole")
	public List<SysPermission> editorRole(SysRole sysRole ) {
		
//		System.out.println("角色id"+sysRole.getId());
//		System.out.println("角色名称"+sysRole.getName());
		
		List<SysPermission> permissionList = sysService.findRolePermissionListByRoleId(sysRole.getId());
		/*EmployeeRole employeeRole = new EmployeeRole();
		employeeRole.setName(sysRole.getName());
		employeeRole.setPermissionList(permissionList);*/
		return permissionList;
	}
	
	
	@RequestMapping("/deleteRole")
	public String deleteRole(String roleId) {
//		System.out.println("删除id"+roleId);
		
		sysService.deleteRole(roleId);
		
		return "redirect:/findRoles";
	}
	@RequestMapping("/saveEditorRolePermission")
	public String saveEditorRolePermission(String roleId,Integer[] permissionIds){
		sysService.saveEditorRolePermission(roleId,permissionIds);
		return "redirect:/findRoles";
	}
	
}
