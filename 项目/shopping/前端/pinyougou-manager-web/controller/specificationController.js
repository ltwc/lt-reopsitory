app.controller('specificationController',function($scope,sepcificationService){
	
	
	
	$scope.addSpecification = function(){
		$scope.entity.specificationOptionList.push({});
	}
	
	$scope.deleteTableRow = function(index){
		$scope.entity.specificationOptionList.splice(index,1);
	}
	
	//根据specificationId查找
	$scope.findSpecificationById = function(spe_id){
		sepcificationService.findSpecificationById(spe_id).success(function(response){
			$scope.entity = response;
		});
	}
	
	
	
	$scope.save = function(){
		var serviceObj = null;
		if($scope.entity.specification.id!=null){
			serviceObj =sepcificationService.updateSpecification($scope.entity)
		}else{
			serviceObj =sepcificationService.addSpecification($scope.entity)
		}
		serviceObj.success(function(response){
			if(response.code == 200){
				$scope.reloadList();
			}else{
				alert(response.message)
			}
		})
	}
	//设置id数组
	$scope.selectedIds = [];
	$scope.updateSelection = function(event,specification_id){
		//判断是否被选中
		if(event.target.checked == true){
			alert(specification_id);
			$scope.selectedIds.push(specification_id);
		}else{
			//取消操作，从数组中删除id
			var index = $scope.selectedIds.indexOf(specification_id) ;
			$scope.selectedIds.splice(index,1);
		}
	}
	
	
	//根据id删除
	$scope.dele = function(){
		if(confirm("你确定要删除吗")){
			sepcificationService.deleteSpecification($scope.selectedIds).success(function(response){
				if(response.code == 200){
					$scope.reloadList();
				}else{
					alert(response.message);
				}
			});
		}
	}
	
	
	//定义查询对象
	$scope.searchOnject = {};
	
	//		<!--//分页控件配置currentPage:当前页   totalItems :总记录数  itemsPerPage:每页记录数  perPageOptions :分页选项  onChange:当页码变更后自动触发的方法--> 
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
		$scope.searchSpecification($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
		}
		
	
	$scope.findByPage = function(page_no,page_size){
		sepcificationService.findSpecificationByPage(page_no,page_size).success(function(response){
			$scope.specificationList = response.rows;
			$scope.paginationConf.totalItems = response.total;
		});
	}
	
	
	
	$scope.searchSpecification = function(page_no,page_size){
		sepcificationService.searchSpecification(page_no,page_size,$scope.searchOnject.specName).success(function(response){
			console.log(response.rows)
			$scope.specificationList = response.rows;
			console.log(response.rows);
			$scope.paginationConf.totalItems = response.total;
		});
	}
	
//	$scope.searchSpecification = function(page_no,page_size){
//		sepcificationService.searchSpecification($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.searchOnject).success(function(response){
//			alert($scope.searchOnject)
//			$scope.specificationList = response.rows;
//			$scope.paginationConf.totalItems = response.total;
//		});
//	}
});