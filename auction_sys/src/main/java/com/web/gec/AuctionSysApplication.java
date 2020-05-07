package com.web.gec;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.web.gec.mapper")
public class AuctionSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionSysApplication.class, args);
	}

}
