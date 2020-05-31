app.service("uploadService",function($http){
	this.uploadFile = function(){
		//向后台传的数据
		var formdata = new FormData();
		//向fordate里面传数据
		formdata.append("file",file.files[0]);
		
		return $http({
			method:'post',
			url:'http://localhost:8899/shopping-content/uploadFile',
			data:formdata,
			headers:{'Content-Type':undefined} ,//Content-Type : text/html  text/plain
			transformRequest: angular.identity
		});
	}
});
