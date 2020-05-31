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
    <title>报销管理</title>

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
<script type="text/javascript">


function jumpPage(page_no){
	//先修改页码
	document.getElementById("page_num").value = page_no; 
	// 手动提交查询表单
	document.getElementById("queryForm").submit();
}

</script>
<body>

<!--路径导航-->
<ol class="breadcrumb breadcrumb_nav">
    <li>首页</li>
    <li>报销管理</li>
    <li class="active">我的报销单</li>
</ol>
<!--路径导航-->

<div class="page-content">
 
   <form id="queryForm"  action="BaoxiaoBillQuery" method="post"  
   			class="form-inline">
   			
   			<!-- 页码 -->
		    <input type="hidden" id="page_num" name="pageNum">
   			
        <div class="panel panel-default">
            <div class="panel-heading">报销单列表</div>
            
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th width="5%">ID</th>
                        <th width="13%">报销金额</th>
                        <th width="15%">标题</th>
                        <th width="20%">备注</th>
                        <th width="15%">时间</th>
                        <th width="10%">状态</th>  
                        <th width="20%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
					<c:forEach var="baoxiao" items="${baoxiaoList}">
	                    <tr>
	                        <td>${baoxiao.id}</td>
	                        <td>${baoxiao.money}</td>
	                        <td>${baoxiao.title}</td>
	                        <td>${baoxiao.remark}</td>
	                        <td>
	                        	<fmt:formatDate value="${baoxiao.creatdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                        </td>
	                            
	                        <c:choose> 
 									<c:when test="${baoxiao.state == 1}"> 
 											<td>审核中 </td> 
  											<td>
										        <a href="${pageContext.request.contextPath}/recordForm?recordId=${baoxiao.id}" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-eye-open"></span>查看审核记录</a>
										        <a href="${pageContext.request.contextPath}/viewCurrentImageByBill?billId=${baoxiao.id}" target="_blank" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-eye-open"></span>查看当前流程图</a>
	                        				</td>
 									</c:when> 
 									<c:otherwise>
 											<td>审核完成 </td>
 											<td>
	                       	  					<a href="deleteBaoxiao?deleteBaoxiaoId=${baoxiao.id}" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> 删除</a>
										        <a href="${pageContext.request.contextPath}/recordForm?recordId=${baoxiao.id}" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-eye-open"></span>查看审核记录</a>
	                        				</td>
	                         		</c:otherwise> 
	                         		
							</c:choose>
	                             
	                    </tr>
					</c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
    <p>
        <a href="javascript:jumpPage(1)" >首页</a>
        <a href="javascript:jumpPage(${page.prePage})">上页</a>
        <a href="javascript:jumpPage(${page.nextPage})">下页</a>
        <a href="javascript:jumpPage(${page.pages})">尾页</a>
<%--        当前页是${page.pageNum},总记录数${page.pagetotal}--%>
    </p>
   <!-- ${pageContext.request.contextPath}/viewTaskForm?taskId=${task.id}" -->
</div>

</body>


</html>