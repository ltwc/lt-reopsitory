package com.web.gec.utils;

public class AuctionPriceException extends Exception {
    private String message;

    public AuctionPriceException() {
        super("价格异常");
    }

    public AuctionPriceException(String message) {
       this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
