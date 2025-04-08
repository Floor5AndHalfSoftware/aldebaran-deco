package com.aldebaran.auth;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class SessionPrincipalResolver
{
   private static final Logger logger = LogManager.getLogger(com.aldebaran.auth.SessionPrincipalResolver.class);
  
  public static final String PRINCIPAL_SESSION_ATTRIBUTE = "__aldebaran_principal";
  public static final String AUTHENTICATED = "authenticated";
  
  public static Principal getUserPrincipal(HttpServletRequest request) {
     return _getUserPrincipal(request);
  }
  
  private static Principal _getUserPrincipal(HttpServletRequest request) {
     HttpSession session = request.getSession(false);
     if (session == null) {
       return null;
    }
     return (Principal)session.getAttribute("__aldebaran_principal");
  }
  
  public static void setUserPrincipal(HttpServletRequest request, Principal principal) {
     HttpSession session = request.getSession(true);
     logger.debug("[com.aldebaran] set principal " + principal.getName());
     session.setAttribute("__aldebaran_principal", principal);
  }
  
  public static void unsetUserPrincipal(HttpServletRequest request, boolean invalidateSession) {
     HttpSession session = request.getSession(false);
     if (session != null) {
       Principal principal = _getUserPrincipal(request);
       if (principal != null) {
         logger.debug("[com.aldebaran] unset principal " + principal.getName());
         session.removeAttribute("__aldebaran_principal");
      } 
       if (invalidateSession) {
         session.invalidate();
      }
    } 
  }
  
  public static void unsetUserPrincipal(HttpServletRequest request) {
     unsetUserPrincipal(request, false);
  }
}


