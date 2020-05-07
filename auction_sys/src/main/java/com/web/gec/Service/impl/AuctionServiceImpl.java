package com.web.gec.Service.impl;

import com.web.gec.Service.AuctionService;
import com.web.gec.mapper.AuctionMapper;
import com.web.gec.mapper.AuctionMapperCustom;
import com.web.gec.mapper.AuctionrecordMapper;
import com.web.gec.pojo.Auction;
import com.web.gec.pojo.AuctionExample;
import com.web.gec.pojo.Auctionrecord;
import com.web.gec.utils.AuctionPriceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
@Service
public class AuctionServiceImpl implements AuctionService {
    @Resource
    private AuctionMapper auctionMapper;
    @Resource
    private AuctionrecordMapper auctionrecordMapper;
    @Resource
    private AuctionMapperCustom auctionMapperCustom;

    @Override
    public List<Auction> findAllAuction(Auction auction) {
        AuctionExample auctionExample = new AuctionExample();
        AuctionExample.Criteria criteria = auctionExample.createCriteria();
        if (auction!=null){
            if (auction.getAuctionname()!=null && !"".equals(auction.getAuctionname())){
                criteria.andAuctionnameLike("%"+auction.getAuctionname()+"%");
            }
            if (auction.getAuctiondesc()!=null && !"".equals(auction.getAuctiondesc())){
                criteria.andAuctiondescLike("%"+auction.getAuctiondesc()+"%");
            }
            if (auction.getAuctionstarttime()!=null){
                criteria.andAuctionstarttimeGreaterThan(auction.getAuctionstarttime());
            }
            if (auction.getAuctionendtime()!=null){
                criteria.andAuctionendtimeLessThanOrEqualTo(auction.getAuctionendtime());
            }
            if (auction.getAuctionstartprice()!=null){
               criteria.andAuctionstartpriceGreaterThanOrEqualTo(auction.getAuctionstartprice());
            }
        }
        auctionExample.setOrderByClause("auctionstarttime desc");
        List<Auction> auctionList = auctionMapper.selectByExample(auctionExample);
        return auctionList;
    }

    @Override
    public void addAuction(Auction auction) {
        auctionMapper.insertSelective(auction);
    }

    @Override
    public Auction findAuctionAndRecordList(int id){
        return auctionMapperCustom.findAuctionAndRecordList(id);
    }

    @Override
    public void saveAuctionRecord(Auctionrecord auctionrecord) throws Exception {
        Auction auction = this.findAuctionAndRecordList(auctionrecord.getAuctionid());
        if (auction.getAuctionendtime().after(new Date()) ==false){
            throw new AuctionPriceException("竞拍商品已经过期 !");
        }else {
            if (auction.getAuctionrecordList()!=null && auction.getAuctionrecordList().size() > 0){
                Auctionrecord maxRecord = auction.getAuctionrecordList().get(0);
                if (auctionrecord.getAuctionprice()<maxRecord.getAuctionprice()){
                    throw  new AuctionPriceException("竞拍价格低于最高价");
                }
            }else {
                if (auctionrecord.getAuctionprice() < auction.getAuctionstartprice()){
                    throw new AuctionPriceException("竞拍价不能小于起拍价");
                }
            }

        }
        auctionrecordMapper.insertSelective(auctionrecord);
    }


}
