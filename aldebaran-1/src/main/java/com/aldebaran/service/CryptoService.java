package com.aldebaran.service;

import com.aldebaran.util.CryptoUtil;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class CryptoService
{
  @Value("${secret.shared.auth}")
  private String secretSharedAuth;
  @Value("${secret.shared.user}")
  private String secretSharedUser;
  @Value("${secret.shared.transaction}")
  private String secretSharedTransaction;
  @Value("${secret.shared.session}")
  private String secretSharedSession;
  @Value("${secret.shared.bonus}")
  private String secretSharedBonus;
  @Value("${secret.shared.sportsbook}")
  private String secretSharedSportsBook;
  @Value("${secret.shared.nonce}")
  private String secretSharedNonce;
  @Value("${secret.shared.operation}")
  private String secretSharedOperation;
  
  public String getSecretSharedAuth() {
     return this.secretSharedAuth;
  }
  
  public String getSecretSharedUser() {
     return this.secretSharedUser;
  }
  
  public String getSecretSharedTransaction() {
     return this.secretSharedTransaction;
  }
  
  public String getSecretSharedSession() {
     return this.secretSharedSession;
  }
  
  public String getSecretSharedSportsBook() {
     return this.secretSharedSportsBook;
  }
  
  public String getSecretSharedNonce() {
     return this.secretSharedNonce;
  }
  
  public String getSecretSharedBonus() {
     return this.secretSharedBonus;
  }
  
  public String getSecretSharedOperation() {
     return this.secretSharedOperation;
  }
  
  public String digest(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    try {
       return CryptoUtil.digest(text);
     } catch (Exception e) {
       return null;
    } 
  }
}


