package com.web.gec.utils;

//import com.web.gec.interceptor.CheckUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    private FileProperties fileProperties;

//    @Autowired
//    private CheckUserInterceptor checkUserInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        System.out.println(fileProperties.getStaticAccessPath());
//        System.out.println(fileProperties.getUploadFileFolder());
        registry.addResourceHandler(fileProperties.getStaticAccessPath()).addResourceLocations("file:"+fileProperties.getUploadFileFolder()+"/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        ArrayList<String> paths = new ArrayList<>();
//        paths.add("/css/**");
//        paths.add("/image/**");
//        paths.add("/js/**");
//        paths.add("/login");
//        paths.add("/doLogin");
//        paths.add("/toRegister");
//        paths.add("/doRegister");
//        paths.add("/defaultKaptcha");
//        registry.addInterceptor(checkUserInterceptor).addPathPatterns("/**").excludePathPatterns(paths);
//    }
}
