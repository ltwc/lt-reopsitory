package com.web.gec.mapper;

import com.web.gec.pojo.Auction;

public interface AuctionMapperCustom {
    Auction findAuctionAndRecordList(int auctionId);
}
