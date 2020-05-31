app.controller('typeTemplateController',function($scope,typeTemplateService,brandService,sepcificationService){
	
//	$controller('baseController',{$scope:$scope});//继承
	
	$scope.brandList = {data:[]}
	//读取品牌信息
	$scope.findBrandList = function(){
		brandService.selectOptionList().success(function(response){
			console.log(response)
			$scope.brandList = {data:response}
		})
	}
	
	$scope.specList = {data:[]}
	//读取品牌信息
	$scope.findSepicification = function(){
		sepcificationService.selectOptionList().success(function(response){
			console.log(response)
			$scope.specList = {data:response}
		})
	}
	
	//保存个更新
	$scope.save = function(){
		var serviceObject;
		$scope.entity.brandIds = JSON.stringify($scope.entity.brandIds);
		$scope.entity.specIds = JSON.stringify($scope.entity.specIds);
		$scope.entity.customAttributeItems = JSON.stringify($scope.entity.customAttributeItems);
		
		if($scope.entity.id!=null){
			serviceObject =typeTemplateService.updateTypeTemplate($scope.entity);
		}else{
			serviceObject = typeTemplateService.addTypeTemplate($scope.entity);
		}
		serviceObject.success(function(response){
			if(response.code ==200){
				$scope.reloadList();
			}else{
				alert(response.message);
			}
			})
		
	}
	
	
//	<!--//分页控件配置currentPage:当前页   totalItems :总记录数  itemsPerPage:每页记录数  perPageOptions :分页选项  onChange:当页码变更后自动触发的方法--> 
	$scope.paginationConf = {
			currentPage : 1,
			totalItems : 5,
			itemsPerPage : 5,
			perPageOptions : [5, 10, 20, 30, 40, 50 ],
			onChange : function() {
			$scope.reloadList();
		}
	};

	//加载
	$scope.reloadList = function(){
		$scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
	}
	
		//定义搜索对象
	$scope.searchEntity = {};
	
	//假如搜索对象有值根据搜索的对象搜索
	$scope.search= function(page_no,page_size){
		typeTemplateService.searchTemplate(page_no,page_size,$scope.searchEntity.searchCondition).success(function(response){
			console.log(response.rows);
//			console.log($scope.searchEntity);
			$scope.list = response.rows;
//			console.log(response.rows);
			$scope.paginationConf.totalItems = response.total;
		});
	}
	
	$scope.addTableRow = function(){
		$scope.entity.customAttributeItems.push({});
	}
	
	$scope.deleTableRow=function(index){
		$scope.entity.customAttributeItems.splice( index,1);
	}
	//根据id查找到对应的模板
	$scope.findOne = function(id){
		typeTemplateService.findById(id).success(function(response){
			$scope.entity = response;
				//转换字符串为json对象（集合）
				$scope.entity.brandIds=  JSON.parse( $scope.entity.brandIds);
				$scope.entity.specIds= JSON.parse($scope.entity.specIds);
				$scope.entity.customAttributeItems = JSON.parse($scope.entity.customAttributeItems);
		})
	}
	
	$scope.jsonToString = function(jsonString,key){
	
		var json = JSON.parse(jsonString);
	
		var value = "";
		
		for(var i  =  0 ;i<json.length ; i++ ){
			if(i >0){
				value += ",";
			}
			value +=json[i][key];
		}
		return value;
	}
	
	$scope.deleteTemp = function(){
		alert($scope.selectedIds);
		typeTemplateService.dele($scope.selectedIds).success(function(response){
			if(response.code == 200){
				alert(response.message);
				$scope.reloadList();
			}else{
				alert(response.message);
			}
		$scope.selectedIds = [];
		});
	}
	
	$scope.selectedIds = [];
	$scope.updateSelection = function(event,id){
		//判断是否被选中
		if(event.target.checked == true){
			alert(id);
			$scope.selectedIds.push(id);
		}else{
			//取消操作，从数组中删除id
			var index = $scope.selectedIds.indexOf(id) ;
			$scope.selectedIds.splice(index,1);
		}
	}
});
