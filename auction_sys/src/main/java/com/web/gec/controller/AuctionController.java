package com.web.gec.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.gec.Service.AuctionService;
import com.web.gec.mapper.AuctionMapperCustom;
import com.web.gec.pojo.Auction;
import com.web.gec.pojo.Auctionrecord;
import com.web.gec.pojo.Auctionuser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class AuctionController {
    public static final int PAGE_SIZE = 5;
    @Resource
    private AuctionService auctionService;


    @RequestMapping("/queryAuctions")
    public String showAuction(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                              @ModelAttribute("condition") Auction auction, HttpSession session, HttpServletRequest request){
        PageHelper.startPage(pageNum,PAGE_SIZE);
        List<Auction> auctionList = auctionService.findAllAuction(auction);
        PageInfo<Auction> pageInfo = new PageInfo<>(auctionList);
        request.setAttribute("auctionList",auctionList);
        request.setAttribute("pageInfo",pageInfo);
        Auctionuser auctionuser = (Auctionuser) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user",auctionuser);
        return "index";
    }
    @RequestMapping("/publishAuctions")
    public String publishAuctions(Auction auction, MultipartFile pic){

        try {
            String path = "d:\\file";
            File targetFile = new File(path,pic.getOriginalFilename());
            pic.transferTo(targetFile);
            auction.setAuctionpic(pic.getOriginalFilename());
            auction.setAuctionpictype(pic.getContentType());
            auctionService.addAuction(auction);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:queryAuctions";
    }
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "addAuction";
    }

    @RequestMapping("/toAuctionDetail/{id}")
    public ModelAndView toAuctionDetail(@PathVariable int id){
        ModelAndView view = new ModelAndView();
        Auction auctionAndRecord= auctionService.findAuctionAndRecordList(id);
        view.addObject("auctionDetail",auctionAndRecord);
        view.setViewName("auctionDetail");
        return view;
    }
    @RequestMapping("/saveAuctionRecord")
    public String saveAuctionRecord(Auctionrecord record, HttpSession session, Model model) throws Exception {
            record.setAuctiontime(new Date());
//            Auctionuser user = (Auctionuser) session.getAttribute("user");
        Auctionuser user = (Auctionuser) SecurityUtils.getSubject().getPrincipal();
        record.setUserid(user.getUserid());
            auctionService.saveAuctionRecord(record);

        return "redirect:/toAuctionDetail/"+record.getAuctionid();
    }
}
