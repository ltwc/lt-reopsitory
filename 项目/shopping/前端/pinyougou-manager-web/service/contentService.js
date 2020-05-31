app.service("contentService",function($http){
	var content_url =  "http://localhost:8899/shopping-content/content/"
	
	this.addContent = function(entity){
		return $http.post(content_url+"addContent",entity);
	}
	
	this.updateContent = function(entity){
		return $http.post(content_url+"updateContent",entity);
	}
	
	this.deleteContent = function(ids){
		return $http.delete(content_url+"deleteContent?ids="+ids);
	}
	
	this.findAllContent = function(page_no,page_size){
		return $http.get(content_url+"findAllContent?pageNum="+page_no+"&pageSize="+page_size);
	}
	
	this.findById = function(id){
		return $http.get(content_url+"findById/"+id);
	}
	
	
});
