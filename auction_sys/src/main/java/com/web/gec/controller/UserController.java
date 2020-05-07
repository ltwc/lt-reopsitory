package com.web.gec.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.web.gec.Service.UserService;
import com.web.gec.pojo.Auctionuser;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request,Model model) {
        String errorException = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        System.out.println("************外");
        if (errorException!=null){
            System.out.println("*****************内");
            if (UnknownAccountException.class.getName().equals(errorException)){
                model.addAttribute("errorMsg","账号错误");
            }
            if (IncorrectCredentialsException.class.getName().equals(errorException)){
                model.addAttribute("errorMsg","密码错误");
            }
            if ("ErrorValideCode".equals(errorException)){
                model.addAttribute("errorMsg","验证码错误");
            }
        }
        return "login";
    }
//    @RequestMapping("/doLogin")
//    public String doLogin(String username, String password, HttpSession session,HttpServletRequest request,String verif){
//        String vrifyCode = (String) session.getAttribute("vrifyCode");
////        if (!vrifyCode.equals(verif)){
////            request.setAttribute("errorMsg","验证码错误");
////            return "login";
////        }
//        Auctionuser user = userService.findByUserName(username);
//        if (user!=null){
//            if (password.equals(user.getUserpassword())){
//                session.setAttribute("user",user);
//                return "redirect:/queryAuctions";
//            }else {
//                request.setAttribute("errorMsg","用户名或密码错误");
//                return "login";
//            }
//        }else{
//            request.setAttribute("errorMsg","用户名或密码错误");
//            return "login";
//        }
//    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @RequestMapping("/toRegister")
    public String toRegister(@ModelAttribute("auctionUser")Auctionuser auctionuser){
        return "register";
    }

    @RequestMapping("/doRegister")
    public String doRegister(Model model, @ModelAttribute("auctionUser") @Validated Auctionuser auctionuser, BindingResult result
            ,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                model.addAttribute(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return "register";
        }
        userService.register(auctionuser);
        return "login";
    }



    @Autowired
    private DefaultKaptcha captchaProducer;

    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("vrifyCode", createText);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
