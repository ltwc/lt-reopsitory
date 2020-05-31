app.service("contentCategoryService",function($http){
	var contentCategoty_url = "http://localhost:8899/shopping-content/contentCategory/";
	
	this.selectOptionList = function(){
		return $http.get(contentCategoty_url+"selectOptionList");
	}
});