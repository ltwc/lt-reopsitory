app.controller("contentController",function($scope,contentService){
	$scope.contentList = [];
	
	//根据类型id查找广告
	$scope.findByCategoryId = function(categoryId){
		contentService.findByCategoryId(categoryId).success(function(response){
			$scope.contentList[categoryId] = response;
		})
	}
	
})
