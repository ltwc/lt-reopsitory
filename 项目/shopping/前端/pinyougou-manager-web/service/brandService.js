app.service('brandService',function($http){
	var brand_url =  "http://localhost:8899/shopping-goods/brand/"
	
	this.findAllBrand = function(){
		return $http.get(brand_url+"findAllBrand");
	}
	
	this.findByPage = function(page_no,page_size){
		return $http.get(brand_url+"findByPage?pageNum="+page_no+"&pageSize="+page_size);
	}
	this.findOneBrand = function(brandId){
		return $http.get(brand_url+"findBrandById/"+brandId);
	}
	
	this.addBrand = function(brand_entity){
		return $http.post(brand_url+"addBrand",brand_entity);
	}
	
	this.updateBrand = function(brand_entity){
		 return $http.post(brand_url+"updateBrand",brand_entity);
	}
	
	this.deleteBrand = function(ids){
		return $http.delete(brand_url+"deleteBrand?ids="+ids);
	}
	this.selectOptionList = function(){
		return $http.get(brand_url+"selectOptionList");
	}
});
