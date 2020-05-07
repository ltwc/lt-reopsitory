package com.web.gec;

import com.web.gec.mapper.AuctionMapper;
import com.web.gec.mapper.AuctionMapperCustom;
import com.web.gec.pojo.Auction;
import com.web.gec.pojo.Auctionrecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuctionSysApplication.class)
public class AuctionSysApplicationTests {
	@Resource
	private AuctionMapperCustom auctionMapperCustom;
//	@Test
//	public void test() {
//		List<Auction> auctions = auctionMapper.selectByExample(null);
//		for (Auction auction : auctions) {
//			System.out.println(auction);
//		}
//	}
	@Test
	public void test(){
		Auction auctionAndRecordList = auctionMapperCustom.findAuctionAndRecordList(3);
		System.out.println("**********");
		for (Auctionrecord auctionrecord : auctionAndRecordList.getAuctionrecordList()) {
			System.out.println("用户名"+auctionrecord.getUser().getUsername());
		}
	}
}
