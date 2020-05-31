app.controller('goodsController',function($scope,$controller,goodsService){
	
	$controller('baseController',{$scope:$scope});//继承
	
	//初次加载
	$scope.search = function(page,rows){
		goodsService.findAllGoods(page,rows).success(function(response){
//			console.log(response.rows);
			$scope.list = response.rows;
			$scope.paginationConf.totalItems = response.total;
			console.log($scope.list)
		});
	}
	
	$scope.updateStatus = function(status){
		goodsService.updateStatus($scope.selectIds,status).success(function(response){
			alert("执行了更新")
			if(response.success){
				 $scope.reloadList();
			}else{
				alert(response.message);
			}
		})
	}
	
	$scope.status = ["未审核","审核通过","审核未通过","关闭"];
	
});