//服务层
app.service('itemCatService',function($http){
	var URL = "http://localhost:8899/shopping-goods-ms/";
	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get(URL+'ItemCat/findAll');			
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get(URL+'ItemCat/findPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get(URL+'ItemCat/findOne/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post(URL+'ItemCat/add',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post(URL+'ItemCat/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get(URL+'ItemCat/delete?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post(URL+'ItemCat/search?page='+page+"&rows="+rows, searchEntity);
	}    	
	
	this.findByParentId = function(parentId){
		return $http.get(URL+"ItemCat/findByParenId/"+parentId);
	}
});
