package com.aldebaran.exception;

import com.aldebaran.service.MailService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;









@ControllerAdvice
public class ControllerExceptionHandler
{
   private static final Logger logger = LogManager.getLogger(com.aldebaran.exception.ControllerExceptionHandler.class);
  
  @Autowired
  MailService mailService;

  
  @ExceptionHandler({Exception.class})
  public void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
     logger.error("[com.aldebaran] UnhandledException", ex);
     if (ex instanceof SecurityException) {
       response.setStatus(401);

    
    }
    else {

      
       response.setStatus(500);
    } 
  }
}


