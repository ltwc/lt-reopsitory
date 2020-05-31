app.service("itemCatService",function($http){
	var URL = "http://localhost:8899/shopping-goods/ItemCat/"
	
	this.findByParentId = function(parentId){
		return $http.get(URL+"findByParenId/"+parentId)
	}
})
	

