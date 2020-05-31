package com.lt.service;

import com.lt.pojo.TbItemCat;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemCatService {
    List<TbItemCat> findByParenId(Long parenId);
    TbItemCat findOne(Long id);
    List<TbItemCat> findAll();
}
