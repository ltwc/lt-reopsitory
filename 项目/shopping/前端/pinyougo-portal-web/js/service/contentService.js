app.service("contentService",function($http){
	var URL = "http://localhost:8899/shopping-content/content/";
	
	this.findByCategoryId = function(categoryId){
		return $http.get(URL+"findByCategoryId/"+categoryId);
	}
})
