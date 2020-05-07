package com.web.gec.Service;

import com.web.gec.pojo.Auction;
import com.web.gec.pojo.Auctionrecord;

import java.util.List;


public interface AuctionService {
    public List<Auction> findAllAuction(Auction auction);
    public void addAuction(Auction auction);
    Auction findAuctionAndRecordList(int id);
    public void saveAuctionRecord(Auctionrecord auctionrecord) throws Exception;
}
