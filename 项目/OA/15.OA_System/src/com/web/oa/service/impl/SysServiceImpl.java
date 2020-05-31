package com.web.oa.service.impl;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.web.oa.mapper.*;
import com.web.oa.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.oa.service.SysService;
import com.web.oa.utils.MD5;



@Service
public class SysServiceImpl implements SysService {

	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	@Autowired
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysRolePermissionMapper sysRolePermissionMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
/*	@Override
	public ActiveUser authenticat(String userName, String password) throws Exception {
		
		
		认证过程：
		根据用户身份（账号）查询数据库，如果查询不到用户不存在
		对输入的密码 和数据库密码 进行比对，如果一致，认证通过
	    
	    //根据用户账号查询数据库
		
		Employee user = this.findSysEmployeeByUserName(userName);
		
		if(user == null){
			//抛出异常
			System.out.println("用户账号不存在");
			return null;
		}
		
		//数据库密码 (md5密码 )
		String password_db = user.getPassword();
		//对输入的密码 和数据库密码 进行比对，如果一致，认证通过
		//对页面输入的密码 进行md5加密 
		String password_input_md5 = new MD5().getMD5ofStr(password);
		if(!password_input_md5.equalsIgnoreCase(password_db)){
			//抛出异常
			System.out.println("密码错误");
			return null;
		}
		
		//得到用户id
		long userid = user.getId();
		
		//根据用户id查询菜单 
		List<SysPermission> menus =this.findMenuListByUserId(userid);
		
		//根据用户id查询权限url
		List<SysPermission> permissions = this.findPermissionListByUserId(userid);
		
		//认证通过，返回用户身份信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(user.getId());
		activeUser.setUsername(user.getName());//用户名称
		
		//放入权限范围的菜单和url
		activeUser.setMenus(menus);
		activeUser.setPermissions(permissions);
		
		return activeUser;
	}*/

	//根据用户账号查询用户信息
	//根据用户账号查询用户信息
	@Override
	public Employee findSysEmployeeByUserName(String username) throws Exception {
		EmployeeExample employeeExample = new EmployeeExample();
		EmployeeExample.Criteria criteria = employeeExample.createCriteria();
		criteria.andNameEqualTo(username);
		
		List<Employee> list = employeeMapper.selectByExample(employeeExample);
		
		
		if(list!=null && list.size()==1){
			return list.get(0);
		}	
		
		return null;
	}

	
	//根据用户id查询权限范围的菜单
	@Override
	public List<SysPermission> findMenuListByUserName(String name) throws Exception {
		
		return sysPermissionMapperCustom.findMenuListByUserName(name);
	}

	//根据用户id查询权限范围的url
	@Override
	public List<SysPermission> findPermissionListByUserName(String name) throws Exception {
		return sysPermissionMapperCustom.findPermissionListByUserName(name);

	}
	
	
	@Override
	public List<TreeMenu> findMenuList(){
		
		return sysPermissionMapperCustom.findMenuList();
	}


	@Override
	public List<SysRole> findAllRole() {
		return sysPermissionMapperCustom.findAllSysRole();
	}


	//添加角色
	@Override
	public void addRole(String rolename, String[] menunid, String[] subMenuid) {
//		String uuid =  UUID.randomUUID().toString();
		Integer i = new Random().nextInt((10000) + 1);

		//判断一级菜单是否为空
		if (menunid!=null && menunid.length>0){
			SysRole role = new SysRole();
			role.setId(i.toString());
			role.setName(rolename);
			role.setAvailable("1");
			sysRoleMapper.insert(role);
			for (String mid : menunid) {
				String uuidP =  UUID.randomUUID().toString();
				SysRolePermission rolePermission = new SysRolePermission();
				rolePermission.setId(uuidP);
				rolePermission.setSysRoleId(i.toString());
				rolePermission.setSysPermissionId(mid);
				sysRolePermissionMapper.insert(rolePermission);
			}

		}
		//判断子菜单是否为空
		if (subMenuid!=null && subMenuid.length>0){
			for (String mid : subMenuid) {
				String uuidP =  UUID.randomUUID().toString();
				SysRolePermission rolePermission = new SysRolePermission();
				rolePermission.setId(uuidP);
				rolePermission.setSysRoleId(i.toString());
				rolePermission.setSysPermissionId(mid);
				sysRolePermissionMapper.insert(rolePermission);
			}
		}

	}

	
	//添加权限
	@Override
	public void addSysPermission(SysPermission sysPermission) {
		sysPermissionMapper.insert(sysPermission);
	}

	
	//查询所有菜单及权限
	@Override
	public List<TreeMenu> findAllMenuList() {
		return sysPermissionMapperCustom.findAllMenuList();
	}


	//查找角色对应的所有权限
	@Override
	public List<SysPermission> findRolePermissionListByRoleId(String id) {
		return sysPermissionMapperCustom.findPermissionListByRoleId(id);
	}


	@Override
	public void deleteRole(String id) {
		SysRole role = sysRoleMapper.selectByPrimaryKey(id);
		role.setAvailable("0");//0表示该角色不可用
		sysRoleMapper.updateByPrimaryKey(role);
		
	}

	@Override
	public void updateUserAndRole(Employee employee) {
//		System.out.println("用户的名字"+employee.getName());
//		System.out.println("进入了");
		SysUserRoleExample example = new SysUserRoleExample();
		SysUserRoleExample.Criteria criteria = example.createCriteria().andSysUserIdEqualTo(employee.getName());
//		System.out.println("执行了修改角色用户表");
		List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(example);
		if (sysUserRoles.size()==1){
			SysUserRole sysUserRole = sysUserRoles.get(0);
			sysUserRole.setSysRoleId(employee.getRole().toString());
			sysUserRoleMapper.updateByPrimaryKeySelective(sysUserRole);
		}
	}

	@Override
	public void saveEditorRolePermission(String roleId, Integer[] permissionIds) {
		System.out.println("角色id----------"+roleId);

		SysRolePermissionExample example = new SysRolePermissionExample();
		SysRolePermissionExample.Criteria criteria = example.createCriteria().andSysRoleIdEqualTo(roleId);
		sysRolePermissionMapper.deleteByExample(example);
		if (permissionIds!=null && permissionIds.length > 0){

			for (Integer permissionId : permissionIds) {
				System.out.println("权限id"+permissionId);

				SysRolePermission sysRolePermission = new SysRolePermission();
				String uuid = UUID.randomUUID().toString();
				sysRolePermission.setId(uuid);
				sysRolePermission.setSysRoleId(roleId);
				sysRolePermission.setSysPermissionId(permissionId.toString());
				sysRolePermissionMapper.insert(sysRolePermission);
			}
		}
	}


}
