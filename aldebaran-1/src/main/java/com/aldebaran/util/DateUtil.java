 package com.aldebaran.util;
 
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.TimeZone;
 
 
 
 
 
 
 
 
 
 
 
 
 public class DateUtil
 {
   public static final String TIMEZONE_UTC = "UTC";
   public static final String TIMEZONE_ITALY = "Europe/Rome";
   
   public static Date creaData(int anno, int mese, int giorno) {
return creaData(anno, mese, giorno, 0, 0, 0, 0);
   }
 
 
 
 
   
   public static Date creaData(int anno, int mese, int giorno, int ore, int minuti, int secondi) {
return creaData(anno, mese, giorno, ore, minuti, secondi, 0);
   }
 
 
 
 
   
   public static Date creaData(int anno, int mese, int giorno, int ore, int minuti, int secondi, int millisecondi) {
Calendar cal = Calendar.getInstance();
cal.setLenient(false);
cal.set(11, ore);
cal.set(12, minuti);
cal.set(13, secondi);
cal.set(14, millisecondi);
cal.set(1, anno);
cal.set(2, mese - 1);
cal.set(5, giorno);
return cal.getTime();
   }
 
   
   public static String format(String pattern, Date date) {
SimpleDateFormat sdf = new SimpleDateFormat(pattern);
return sdf.format(date);
   }
 
   
   public static String format(String pattern, Date date, String timeZoneId) {
SimpleDateFormat sdf = new SimpleDateFormat(pattern);
sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));
return sdf.format(date);
   }
 
   
   public static Date parse(String pattern, String datetext) throws ParseException {
SimpleDateFormat sdf = new SimpleDateFormat(pattern);
return sdf.parse(datetext);
   }
 
 
   
   public static Date parse(String pattern, String datetext, String timeZoneId) throws ParseException {
SimpleDateFormat sdf = new SimpleDateFormat(pattern);
sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));
return sdf.parse(datetext);
   }
   
   public static Date now() {
return new Date();
   }
   
   public static Date today() {
Calendar cal = Calendar.getInstance();
return creaData(cal.get(1), cal.get(2) + 1, cal.get(5), 0, 0, 0, 0);
   }
 
   
   public static Date addDays(Date date, int days) {
Calendar cal = Calendar.getInstance();
cal.setTime(date);
cal.add(5, days);
return cal.getTime();
   }
 
   
   public static Date addHours(Date date, int hours) {
     Calendar cal = Calendar.getInstance();
     cal.setTime(date);
     cal.add(11, hours);
     return cal.getTime();
   }
 
   
   public static Date addMinutes(Date date, int minutes) {
     Calendar cal = Calendar.getInstance();
     cal.setTime(date);
     cal.add(12, minutes);
     return cal.getTime();
   }
 
 
 
   
   public static Date giornoPrecedente(Date date) {
     return addDays(date, -1);
   }
 
 
 
   
   public static Date giornoSuccessivo(Date date) {
     return addDays(date, 1);
   }
   
   public static Boolean isFormatted(String datetext, String pattern) {
     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
     try {
       sdf.parse(datetext);
     }
     catch (Exception e) {
       return false;
     } 
     return true;
   }
 }


