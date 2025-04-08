/*   */ package com.aldebaran.util;
/*   */ 
/*   */ import java.util.regex.Pattern;
/*   */ 
/*   */ public class RegexUtil
/*   */ {
/*   */   public static boolean matches(String regex, String input) {
     return Pattern.matches(regex, input);
/*   */   }
/*   */   
/*   */   public static final String EMAIL = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
/*   */   public static final String TAXCODE = "^[A-Za-z0-9]*\\d+[A-Za-z0-9]*$";
/*   */ }


