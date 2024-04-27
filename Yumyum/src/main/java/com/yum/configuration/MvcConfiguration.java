package com.yum.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer{
	
	/*
	 * @Override public void addViewControllers(ViewControllerRegistry registry) {
	 * registry.addRedirectViewController("/","login"); }
	 */
	

	/*
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * registry.addInterceptor(new CartInterceptor())
	 * .excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
	 * 
	 * // 참고 : https://congsong.tistory.com/24 }
	 */
	
//	  /*
//     * 로그인 인증 Interceptor 설정
//     * */
//    @Autowired
//    CertificationInterceptor certificationInterceptor;
//    
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(certificationInterceptor)
//                .addPathPatterns("/**/*.do");
//    }
		// 참조 : https://www.leafcats.com/40
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
		multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); // 파일당 업로드 크기 제한 (5MB)
		return multipartResolver;
		
	}
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/yumyum/**")
        .addResourceLocations("file:///C:/User/h/Picture/");
	
	}
}