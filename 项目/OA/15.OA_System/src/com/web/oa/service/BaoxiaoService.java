package com.web.oa.service;

import java.util.List;

import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Baoxiaobill;

public interface BaoxiaoService {


	public Baoxiaobill findBaoxiaoBillById(Integer id);
	
	public List<Baoxiaobill> findBaoxiaoBillListByUser(Integer id);

	public void deleteBaoxiaoBillById(Integer id);

	
	
}
