app.controller("itemCatController",function($scope,itemCatService){
	
	$scope.selectList = function(p_entity){
		if($scope.grade == 1){
			$scope.entity_1 = null;
			$scope.entity_2 = null;
		}
		if($scope.grade == 2){
			$scope.entity_1 = p_entity;
			$scope.entity_2 = null;
		}
		if($scope.grade == 3){
			$scope.entity_2 = p_entity;
		}
		
		$scope.findParentId(p_entity.id);
	}
	
	
	$scope.findParentId = function (parentId){
		itemCatService.findByParentId(parentId).success(function(response){
			$scope.list = response;
			console.log(response)
		})
	}
	
	
	$scope.grade = 1;
	$scope.setGrade = function(value){
		$scope.grade = value;
	}
})