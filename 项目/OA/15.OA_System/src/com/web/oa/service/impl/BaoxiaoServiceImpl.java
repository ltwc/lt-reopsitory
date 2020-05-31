package com.web.oa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.oa.mapper.BaoxiaobillMapper;
import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Baoxiaobill;
import com.web.oa.pojo.BaoxiaobillExample;
import com.web.oa.service.BaoxiaoService;

@Service
public class BaoxiaoServiceImpl implements BaoxiaoService{

	
	@Autowired
	private BaoxiaobillMapper baoxiaobillMapper;
	
	
	@Override
	public Baoxiaobill findBaoxiaoBillById(Integer id){
		Baoxiaobill bill = baoxiaobillMapper.selectByPrimaryKey(id);
		return bill;
	}
	
	
   //查找符合user_id的报销单
	@Override
	public List<Baoxiaobill> findBaoxiaoBillListByUser(Integer id) {
		BaoxiaobillExample example = new BaoxiaobillExample();
		BaoxiaobillExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(id);
		return baoxiaobillMapper.selectByExample(example);
	}
	

	@Override
	public void deleteBaoxiaoBillById(Integer id) {
		baoxiaobillMapper.deleteByPrimaryKey(id);
	}

}
