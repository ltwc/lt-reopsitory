package com.web.gec.Service;

import com.web.gec.pojo.Auctionuser;
import org.springframework.stereotype.Service;


public interface UserService {
    Auctionuser findByUserName(String username);
    void register(Auctionuser auctionuser);

}
