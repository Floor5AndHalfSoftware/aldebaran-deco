package com.aldebaran.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;







public class CryptoUtil
{
  public static String digest(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
     MessageDigest md = MessageDigest.getInstance("SHA-256");
     md.reset();
     byte[] buffer = pwd.getBytes("UTF-8");
     md.update(buffer);
     byte[] digest = md.digest();
    
     String hexStr = "";
     for (int i = 0; i < digest.length; i++) {
       hexStr = hexStr + hexStr;
    }
    
     return hexStr;
  }
}


