<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>系统管理</title>

    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="css/content.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="js/jquery.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

<!--路径导航-->
<ol class="breadcrumb breadcrumb_nav">
    <li>首页</li>
    <li>用户管理</li>
    <li class="active">角色添加</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <form class="form-inline" action="addRole" method="post">
        <div class="panel panel-default">
            <div class="panel-heading">添加角色&nbsp;&nbsp;&nbsp;
            
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    
                    <tr>
                    	<th>
               				角色名称
               				<input class="form-control" type="text" name="rolename" />
               				<input type="submit" class="btn btn-primary" value="保存角色和权限">
                    	</th>
                    </tr>
                    
                </table>
               
            </div>
        </div>
        </div>
        
        <div class="panel panel-default">
        <div class="panel-heading">权限分配列表&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary" title="新建" data-toggle="modal" data-target="#createUserModal">新建权限</button>
		<div class="table-responsive">        
		<table class="table table-striped table-hover">
                    
                    <tr>
                    	<th>角色名称</th>
                    	<th>子菜单和权限</th>
                    </tr>
                    
		           <c:forEach var="menu" items="${menusList}">
		               	<tr>
		               		 <td>
		               		 	<input class="" type="checkbox" name="menunid" value="${menu.id}"/>${menu.name}
		               		 </td>
		               		 <td>
		               		 <c:forEach var="subMenu" items="${menu.children}">
		               		 	
		               		 	<input type="checkbox" name="subMenuid" value="${subMenu.id}"/>${subMenu.name}  ${subMenu.type}
		               		 	<br>
		               		 </c:forEach>
		               		 </td>
		               	</tr>
		           </c:forEach>
                    
        </table>
        </div>
        </div>
        </div>
    </form>
    
    <!--添加用户 编辑窗口 -->
	<div class="modal fade" id="createUserModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
	  <form id="permissionForm" action="savePermission" method="post">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">新建权限</h3>
				</div>
				<div class="modal-body">
					<table class="table table-bordered table-striped" width="800px">
						<tr>
							<td>权限名称</td>
							<td><input class="form-control" name="name" placeholder="权限名称"></td>
						</tr>					
						<tr>
							<td>type</td>
							<td><input class="form-control"  name="type" placeholder="type"></td>
						</tr>
						<tr>
						    <td>url</td>
						    <td><input class="form-control" name="url" placeholder="url">
						    </td>
						</tr>
						<tr>
						    <td>percode</td>
						    <td><input class="form-control" name="percode" placeholder="percode">
						    </td>
						</tr>
						<tr>
						    <td>parentid</td>
						    <td><input class="form-control" name="parentid" placeholder="parentid">
						    </td>
						</tr>
						
						
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" data-dismiss="modal"
						aria-hidden="true" onclick="javascript:document.getElementById('permissionForm').submit()">保存</button>
					<button class="btn btn-default" data-dismiss="modal"
						aria-hidden="true">关闭</button>
				</div>
			</div>
		</div>
	  </form>
	</div>
    
    
</div>

</body>
</html>