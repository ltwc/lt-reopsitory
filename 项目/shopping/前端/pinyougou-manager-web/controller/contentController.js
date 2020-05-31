app.controller('contentController',function($scope,$controller,uploadService,contentCategoryService,contentService){
	
	$controller('baseController',{$scope:$scope});//继承
	
	
	//查询所有的广告分类
	$scope.findAllContentCategoryList = function(){
		contentCategoryService.selectOptionList().success(function(response){
			$scope.categoryList = response;
		})
	}
	//保存和修改
	$scope.save = function(){
		var serviceObj;
		
		if($scope.entity.status == true){
			$scope.entity.status = 1;
		}else{
			$scope.entity.status = 0;
		}
		
		
		if($scope.entity.id!=null){
			serviceObj = contentService.updateContent($scope.entity);
		}else{
			serviceObj = contentService.addContent($scope.entity);
		}
		
		
		serviceObj.success(
			function(response){
				alert(response)
				if(response.code==200){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
//		serviceObj.success(function(response){
//			alert("执行了")
//			if(response.code == 200){
//				alert(response.message)
//				$scope.reloadList();
//			}else{
//				alert(response.message)
//			}
//		})
	}
	//根据id查找广告详细信息
	$scope.findById = function(id){
		contentService.findById(id).success(function(response){
			
			$scope.entity = response;
			
			if($scope.entity.status == 1){
			$scope.entity.status = true;
			}else{
				$scope.entity.status = false;
			}
		})
	}
	
	$scope.uploadFile = function(){
		uploadService.uploadFile().success(function(response){
			if(response.success){
				$scope.entity.pic = response.message;
			}else{
				alert(response.message);
			}
			
		})
	}
	
	
	//初次加载
	$scope.search = function(page,rows){
		contentService.findAllContent(page,rows).success(function(response){
//			console.log(response.rows);
			$scope.list = response.rows;
			$scope.paginationConf.totalItems = response.total;
		});
	}
	
	$scope.dele = function(){
		contentService.deleteContent($scope.selectIds).success(function(response){
			alert($scope.selectIds)
			if(response.code == 200){
					$scope.reloadList();
			}else{
				alert(response.message);
			}
		});
	}
	
	$scope.status = ["无效","有效"];
	
//	$scope.jsonToString=function(jsonString,key){
//		
//		var json= JSON.parse(jsonString);
//		var value="";
//		
//		for(var i=0;i<json.length;i++){
//			if(i>0){
//				value+=",";
//			}			
//			value +=json[i][key];			
//		}
//				
//		return value;
//	}
});
