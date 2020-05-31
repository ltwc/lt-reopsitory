package com.web.oa.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Employee;
import com.web.oa.pojo.SysPermission;
import com.web.oa.pojo.TreeMenu;
import com.web.oa.service.EmployeeService;
import com.web.oa.service.SysService;


public class CustomRealm extends AuthorizingRealm{

	@Autowired
	private SysService sysService;
	
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		System.out.println("正在认证用户");
		
		//1.获取账号
		String username =  (String) token.getPrincipal();//用户输入的账号
		
		Employee loginUser = null;
		try {
			loginUser = sysService.findSysEmployeeByUserName(username);
			if(loginUser == null){
				return null; //报认证失败 帐号错误：org.apache.shiro.authc.UnknownAccountException:
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String password_db = loginUser.getPassword(); //密文
		
		
		String salt_db = loginUser.getSalt();
	
		
		//List<SysPermission> menus = null;
		List<TreeMenu> menus = null;
 		
		try {
			//查询菜单的信息
			//menus = sysService.findMenuListByUserId(loginUser.getId());
			
			menus = sysService.findMenuList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//进一步封装表示用户身份信息的pojo对象
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(loginUser.getId());
		activeUser.setUsername(loginUser.getName());
		activeUser.setManagerId(loginUser.getManagerId());
		activeUser.setMenusList(menus);
		
		//SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUser, password_db, "CustomRealm");

		
		//第一个参数：用户信息(也就是认证主体的身份信息) 第二个参数：数据库的密码（不是参数密码）
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, password_db, ByteSource.Util.bytes(salt_db), "CustomRealmMd5");
		return info;
	}
	
	
	//授权：查询出当前合法用户的使用权限
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		
		System.out.println("开始查权限");
		//查询当前用户
		ActiveUser activeUser = (ActiveUser) principal.getPrimaryPrincipal();
		
		System.out.println("用户名："+activeUser.getUsername());
		//构建动态的权限设置
		//根据合法用户的信息查询对应的所有权限即可
		
		//用户对应的权限表
		List<SysPermission> list= null;
		try {
			list = sysService.findPermissionListByUserName(activeUser.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//权限集合
		List<String> permissions = new ArrayList<>();
		
		for (SysPermission sysPermission : list) {
			System.out.println("权限："+sysPermission.getPercode());
			permissions.add(sysPermission.getPercode());
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissions);
	
		return info;
	}

	
	

}
