package com.aldebaran.config;

import com.aldebaran.interceptor.RoleCheckInterceptor;
import com.aldebaran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig
  implements WebMvcConfigurer
{
  @Autowired
  UserService userService;
  
  public void addInterceptors(InterceptorRegistry registry) {
     registry.addInterceptor((HandlerInterceptor)new RoleCheckInterceptor(this.userService));
  }
}


