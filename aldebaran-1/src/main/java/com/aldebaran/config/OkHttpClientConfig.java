package com.aldebaran.config;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;






@Configuration
public class OkHttpClientConfig
{
   public static final Integer CONNECT_TIMEOUT = 120000;
   public static final Integer READ_TIMEOUT = 120000;
  public static final Boolean ignoreCertificate = true;
  
  @Bean
  public OkHttpClient client() {
     OkHttpClient.Builder builder = new OkHttpClient.Builder();
     if (ignoreCertificate) {
       builder = configureToIgnoreCertificate(builder);
    }
     return 
       builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
       .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
       .build();
  }



  
  private static OkHttpClient.Builder configureToIgnoreCertificate(OkHttpClient.Builder builder) {
    try {
       TrustManager[] trustAllCerts = { (TrustManager)new Object() };


















      
       SSLContext sslContext = SSLContext.getInstance("SSL");
       sslContext.init(null, trustAllCerts, new SecureRandom());
      
       SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
      
       builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
       builder.hostnameVerifier((HostnameVerifier)new Object());




    
    }
     catch (Exception exception) {}

    
     return builder;
  }
}


