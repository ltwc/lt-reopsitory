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
    <li class="active">角色管理</li>
</ol>
<!--路径导航-->

<div class="page-content">
    <form class="form-inline">
        <div class="panel panel-default">
            <div class="panel-heading">角色列表</div>
            
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th width="80%">ID</th>
                        
                        <th width="20%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
					<c:forEach var="role" items="${RoleList}">
	                    <tr>
	                        <td>${role.name}</td>
	                       
	                        <td>
	                            <a href="#" onclick="viewEditorPermission('${role.name}','${role.id}')" class="btn btn-info btn-xs" data-toggle="modal" data-target="#editModal"><span class="glyphicon glyphicon-edit"></span> 编辑</a>
				 				<a href="deleteRole?roleId=${role.id}" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> 删除</a>
				 					
	                        </td>
	                    </tr>
					</c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>

<!-- 查看用户角色权限窗口 -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" >
	<div class="modal-content">
		<div class="modal-header">
			<button type="button"  onclick="cleanTable()" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">权限列表</h3>
		</div>
		<div class="modal-body" >
		<form id="editpermissionForm" action="saveEditorRolePermission" method="post">
			 <table class="table table-bordered" id="cleantable"  width="800px">	
			    <thead>	
			      	<tr>         
						<th>角色</td>
						<th>权限</th>
	                </tr>
                </thead>
                <tbody id="cleantable">
				<input type="hidden" name="roleId" id="role_id">
               	<c:forEach var="menu" items="${menusList}">
		               	<tr>
		               		 <td>
		               		 	<input id="chk${menu.id}" type="checkbox" name="permissionIds" value="${menu.id}"/>${menu.name}
		               		 </td>
		               		 <td>
		               		 <c:forEach var="subMenu" items="${menu.children}">
		               		 	
		               		 	<input type="checkbox" id="chk${subMenu.id}" name="permissionIds" value="${subMenu.id}"/>${subMenu.name} [${subMenu.type}]
		               		 	<br>
		               		 </c:forEach>
		               		 </td>
		               	</tr>
		         </c:forEach>
		         </tbody>
               
			 </table>
			 </form>				
		</div>
		<div class="modal-footer">
		     <button type="button" class="btn btn-success" data-dismiss="modal"
						aria-hidden="true" onclick="javascript:document.getElementById('editpermissionForm').submit()">保存</button>
			 <button onclick="cleanTable()" class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
		</div>
	  </div>
	</div>
	<script type="text/javascript">
		
		function viewEditorPermission(r_name,r_id) {
			$("#role_id").attr("value",r_id);
			$.ajax({
				url:'editorRole',
				type:'post',
				data:{
					name:r_name,
					id:r_id
				},
				dataType:'json',
				success:function(permissionList) {
					
					
					$.each(permissionList,function(i,perm){
						$('#chk'+perm.id).attr('checked',true);/*遍历判断  */
					});
					$("#editModal").modal("show");
					
				},
				error:function(req,error) {
					alert(req.status+':'+error);
				}
			});
		}
		
		
		function cleanTable() {
			// alert("清空");
			// $('#cleantable').datagrid('clearChecked')='none';//清除所有勾选的行。
		}
		
	</script>
</div>


</body>
</html>