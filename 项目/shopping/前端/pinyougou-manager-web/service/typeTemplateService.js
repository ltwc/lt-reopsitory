app.service('typeTemplateService',function($http){
	var typeTemplate_url =  "http://localhost:8899/shopping-goods/typeTemplate/"
	
	this.addTypeTemplate = function(entity){
		return $http.post(typeTemplate_url+"addTypeTemplate",entity);
	};
	
	this.updateTypeTemplate = function(entity){
		return $http.post(typeTemplate_url+"updateTypeTemplate",entity);
	};
	
	this.dele = function(ids){
		return $http.delete(typeTemplate_url+"deleteTypeTemplate?ids="+ids);
	}
	
	this.findById = function(id){
		return $http.get(typeTemplate_url+"findById/"+id);
	};
	
	this.searchTemplate = function(page_no,page_size,searchCondition){
		return $http.get(typeTemplate_url+"findTemplateByPageAndName?pageNum="+page_no+"&pageSize="+page_size+"&tempName="+searchCondition);
	};
	this.findBySpecList = function(id){
		return $http.get(typeTemplate_url+"findSpecList/"+id);
	}
})
