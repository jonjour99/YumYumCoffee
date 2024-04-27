package com.yum.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	
 	private String connectPath = "/imagePath/**";
 	//경로 신경쓰시오!
 	//윈도우
    private String resourcePath = "file:///C:/Users/"+System.getProperty("user.name")+"/Pictures/yumyum/";
    //mac 사용자여 테스트가 필요하다
    //private String resourcePath = "file:/Users/"+System.getProperty("user.name")+"/Pictures/yumyum/";


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);
    }

}
