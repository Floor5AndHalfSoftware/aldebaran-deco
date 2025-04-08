package com.aldebaran.interceptor;

import com.aldebaran.auth.APrincipal;
import com.aldebaran.service.UserService;
import com.aldebaran.util.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import java.lang.reflect.Method;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;




public class RoleCheckInterceptor
  implements HandlerInterceptor
{
  private UserService userService;
  
  public RoleCheckInterceptor(UserService userService) {
     this.userService = userService;
  }



  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     if (!(handler instanceof HandlerMethod)) {
       return true;
    }
     Method method = ((HandlerMethod)handler).getMethod();
     if (method == null) {
       return true;
    }
    
     APrincipal ap = UserUtil.getUser(request);
    
     if (method.isAnnotationPresent((Class)DenyAll.class)) {
       throw new SecurityException("permission denied");
    }
     if (method.isAnnotationPresent((Class)PermitAll.class)) {
       return true;
    }
    
     RolesAllowed ra = method.<RolesAllowed>getAnnotation(RolesAllowed.class);
     if (ra == null) {
       Class<?> methodClass = ((HandlerMethod)handler).getBean().getClass();
       if (methodClass.isAnnotationPresent((Class)DenyAll.class)) {
         throw new SecurityException("permission denied");
      }
       if (methodClass.isAnnotationPresent((Class)PermitAll.class)) {
         return true;
      }
       ra = methodClass.<RolesAllowed>getAnnotation(RolesAllowed.class);
    } 
    
     if (ra != null) {
       if (ap != null) {
         JsonNode result = this.userService.keepAlive(ap.getUserId(), ap.getSessionId());
         if (result.has("result") && result.get("result").asInt() == -100) {
           response.setHeader("Refresh", "0; URL=" + request.getContextPath());
        }
         return true;
      } 
      
       throw new SecurityException("permission denied");
    } 
    
     return true;
  }
}


