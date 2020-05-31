app.controller('brandController',function($scope,brandService){
	$scope.loadAllBrand = function(){
		brandService.findAllBrand().success(function(response){
			$scope.brandList = response;
		});
	};
	
//	根据品牌id查找
	$scope.findOneBrand = function(brand_id){
		brandService.findOneBrand(brand_id).success(function(response){
			$scope.entity = response;
		})
	}
	
	$scope.save = function(){
		var serviceObj = null;
		if($scope.entity.id!=null){
			serviceObj =brandService.updateBrand($scope.entity)
		}else{
			serviceObj =brandService.addBrand($scope.entity)
		}
		serviceObj.success(function(response){
			if(response.code == 200){
				$scope.reloadList();
			}else{
				alert(response.message)
			}
		})
	}
	
	//删除模块
	
	$scope.selectedIds = [];
	$scope.updateSelection = function(event,news_id){
		//判断是否被选中
		if(event.target.checked == true){
			alert(news_id);
			$scope.selectedIds.push(news_id);
		}else{
			//取消操作，从数组中删除id
			var index = $scope.selectedIds.indexOf(news_id) ;
			$scope.selectedIds.splice(index,1);
		}
	}
	
	$scope.dele = function(){
		if(confirm("你确定要删除吗")){
			alert($scope.selectedIds)
			brandService.deleteBrand($scope.selectedIds).success(function(response){
				if(response.code == 200){
					$scope.reloadList();
				}else{
					alert(response.message);
				}
			});
		}
	}
	
	
		//		 			<!--//分页控件配置currentPage:当前页   totalItems :总记录数  itemsPerPage:每页记录数  perPageOptions :分页选项  onChange:当页码变更后自动触发的方法--> 
	$scope.paginationConf = {
			currentPage : 1,
			totalItems : 5,
			itemsPerPage : 5,
			perPageOptions : [5, 10, 20, 30, 40, 50 ],
			onChange : function() {
			$scope.reloadList();
		}
	};
	
	$scope.reloadList = function(){
		$scope.findByPage($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
	}
	
	$scope.findByPage = function(page_no,page_size){
		brandService.findByPage(page_no,page_size).success(function(response){
			$scope.brandList = response.rows;
			$scope.paginationConf.totalItems = response.total;
		});
	}
	
});