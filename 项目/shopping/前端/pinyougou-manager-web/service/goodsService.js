app.service("goodsService",function($http){
	var URL = "http://localhost:8899/shopping-sellergoods/"
	this.findAllGoods = function(page_no,page_Size){
		return $http.get(URL+"goods-ms/findAllGoodsByPage?pageNum="+page_no+"&pageSize="+page_Size);
	}
	
	this.updateStatus = function(ids,status){
		return  $http.get(URL+"goods-ms/updateStatus?ids="+ids+"&status="+status);
	}
})