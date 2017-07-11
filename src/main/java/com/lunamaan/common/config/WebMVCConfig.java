package com.lunamaan.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lunamaan.account.interceptor.LoginInterceptor;

@Configuration
public class WebMVCConfig  extends WebMvcConfigurerAdapter{
	 @Autowired
	 LoginInterceptor loginInterceptor;
	 
	 @Override
	 public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(loginInterceptor)
		 	.addPathPatterns("/member/**");
	 }
}
