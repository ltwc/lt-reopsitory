app.service('sepcificationService',function($http){
	var specification_url =  "http://localhost:8899/shopping-goods/specification/"
	
	this.findSpecificationByPage = function(page_no,page_size){
		return $http.get(specification_url+"findSpecificationByPage?pageNum="+page_no+"&pageSize="+page_size);
	}
	
	this.findSpecificationById = function(id){
		return $http.get(specification_url+"/findSpecificationById/"+id);
	}
	
	this.updateSpecification = function(specification){
		return $http.post(specification_url+"updateSpecification",specification);
	}
	
	this.addSpecification = function(specification){
		return $http.post(specification_url+"addSpecification",specification);
	}
	
	this.deleteSpecification = function(ids){
		return $http.delete(specification_url+"deleteSpecification?ids="+ids);
	}
	

	this.searchSpecification = function(page_no,page_size,search_condition){
		return $http.get(specification_url+"findSpecificationByPage?pageNum="+page_no+"&pageSize="+page_size+"&searchCondition="+search_condition);
	}
	
	this.selectOptionList = function(){
		return $http.get(specification_url+"selectOptionList");
	}
});