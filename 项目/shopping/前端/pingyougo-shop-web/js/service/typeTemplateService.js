//服务层
app.service('typeTemplateService',function($http){
	var URL = "http://localhost:8899/shopping-goods-ms/";
	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get(URL+'typeTemplate/findAll');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get(URL+'typeTemplate/findTemplateByPageAndName?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get(URL+'typeTemplate/findById/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post(URL+'typeTemplate/addTypeTemplate',entity);
	}
	//修改 
	this.update=function(entity){
		return  $http.post(URL+'typeTemplate/updateTypeTemplate',entity);
	}
	//删除
	this.dele=function(ids){
		return $http.get(URL+'typeTemplate/deleteTypeTemplate?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post(URL+'typeTemplate/search?page='+page+"&rows="+rows, searchEntity);
	}    	
	
	this.findBySpecList=function(id){
		return $http.get(URL+'typeTemplate/findSpecList/'+id);
	}
});
