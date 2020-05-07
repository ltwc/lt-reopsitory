package com.web.gec.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView view = new ModelAndView();
        AuctionPriceException auctionPriceException = null;
        if (e instanceof AuctionPriceException){
            auctionPriceException = (AuctionPriceException) e;
            view.addObject("errorMsg",auctionPriceException.getMessage());
        }else {
            view.addObject("errorMsg",e.getMessage());
        }
        view.setViewName("error");
        return view;
    }
}
