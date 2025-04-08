package com.aldebaran.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class MathUtil
{
  public static String formattaEuro(Object importo) {
    try {
       double euro = 0.0D;
       DecimalFormat df = new DecimalFormat("###,##0.00", DecimalFormatSymbols.getInstance(Locale.ITALY));
       if (importo instanceof Double) {
         euro = (Double)importo;
      }
       else if (importo instanceof Float) {
         euro = (Float)importo;
      }
       else if (importo instanceof String) {
         euro = Double.parseDouble("" + importo);
      } else {
        
         long eurocent = Long.parseLong(importo.toString());
         euro = eurocent / 100.0D;
      } 
       return df.format(euro);
    }
     catch (NullPointerException ne) {
       return null;
    } 
  }

  
  public static String formatta1Decimale(Object importo) {
    try {
       double euro = 0.0D;
       DecimalFormat df = new DecimalFormat("###,##0.0", DecimalFormatSymbols.getInstance(Locale.ITALY));
       if (importo instanceof Double) {
         euro = (Double)importo;
      }
       else if (importo instanceof Float) {
         euro = (Float)importo;
      }
       else if (importo instanceof String) {
         euro = Double.parseDouble("" + importo);
      } else {
        
         long eurocent = Long.parseLong(importo.toString());
         euro = eurocent / 100.0D;
      } 
       return df.format(euro);
    }
     catch (NullPointerException ne) {
       return null;
    } 
  }

  
  public static double arrotonda(double numero, double nCifreDecimali) {
     return Math.round(numero * Math.pow(10.0D, nCifreDecimali)) / Math.pow(10.0D, nCifreDecimali);
  }
  
  public static long arrotondaIntero(float n) {
     return Math.round(n);
  }
  
  public static long eurocentLong(String numero) {
     BigDecimal amount = new BigDecimal(numero);
     BigDecimal amountEuroCent = amount.multiply(new BigDecimal(100));
     return amountEuroCent.longValue();
  }
  
  public static int eurocentInt(String numero) {
     BigDecimal amount = new BigDecimal(numero);
     BigDecimal amountEuroCent = amount.multiply(new BigDecimal(100));
     return amountEuroCent.intValue();
  }
}


