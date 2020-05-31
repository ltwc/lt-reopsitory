app.service("searchService",function($http){
	var search_url ="http://localhost:8899/shopping-search/itemsearch-ms/";
	
	this.search = function(searchMap){
		return $http.post(search_url+"search",searchMap);
	}
})