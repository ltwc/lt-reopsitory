package com.web.gec.Service.impl;

import com.web.gec.Service.UserService;
import com.web.gec.mapper.AuctionuserMapper;
import com.web.gec.pojo.Auctionuser;
import com.web.gec.pojo.AuctionuserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuctionuserMapper auctionuserMapper;
    @Override
    public Auctionuser findByUserName(String username) {
        AuctionuserExample auctionuserExample = new AuctionuserExample();
        AuctionuserExample.Criteria criteria = auctionuserExample.createCriteria().andUsernameEqualTo(username);
        List<Auctionuser> userList = auctionuserMapper.selectByExample(auctionuserExample);
        if (userList!=null && userList.size()>0){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public void register(Auctionuser auctionuser) {
        auctionuserMapper.insertSelective(auctionuser);
    }
}
