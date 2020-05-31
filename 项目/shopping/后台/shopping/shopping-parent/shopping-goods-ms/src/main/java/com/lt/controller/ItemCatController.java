package com.lt.controller;

import com.lt.pojo.RespBean;
import com.lt.pojo.TbItemCat;
import com.lt.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ItemCat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @GetMapping("findByParenId/{parentId}")
    public List findByParenId(@PathVariable("parentId") Long parentId){
        System.out.println("parentId"+parentId);
        return  itemCatService.findByParenId(parentId);
    }
    @GetMapping("findOne/{id}")
    public TbItemCat findOne(@PathVariable("id") Long id){
        return itemCatService.findOne(id);
    }
    @GetMapping("findAll")
    public List<TbItemCat> findAll(){
        return itemCatService.findAll();
    }
}
