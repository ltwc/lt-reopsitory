package com.lt.service.impl;

import com.lt.mapper.TbItemCatMapper;
import com.lt.pojo.TbItemCat;
import com.lt.pojo.TbItemCatExample;
import com.lt.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public List<TbItemCat> findByParenId(Long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        System.out.println("parentId"+parentId);
        if (parentId!= null){

            TbItemCatExample.Criteria criteria = example.createCriteria().andParentIdEqualTo(parentId);
        }


        return itemCatMapper.selectByExample(example);
    }

    @Override
    public TbItemCat findOne(Long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TbItemCat> findAll() {
        return itemCatMapper.selectByExample(null);
    }
}
