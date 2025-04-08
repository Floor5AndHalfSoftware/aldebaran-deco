 package com.aldebaran.util;
 
 import com.maxmind.geoip2.DatabaseReader;
 import com.maxmind.geoip2.model.CountryResponse;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.net.InetAddress;
 import java.net.URI;
 import java.net.http.HttpClient;
 import java.net.http.HttpRequest;
 import java.net.http.HttpResponse;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.nio.file.Paths;
 import java.util.List;
 import java.util.stream.Collectors;
 import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.scheduling.annotation.Scheduled;
 import org.springframework.stereotype.Component;
 
 
 @Component
 public class GeoliteUtil
 {
private static final Logger logger = LogManager.getLogger(com.aldebaran.util.GeoliteUtil.class);
   
   @Value("${geolite.path:null}")
   private String geolitePath;
   
   @Value("${geolite.url:null}")
   private String geoliteUrl;
   
   @Value("${geolite.enabled:false}")
   private boolean geoliteEnabled;
   
   @Value("${geolite.country:null}")
   private String geoliteCountry;
   
private final HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
   
private DatabaseReader reader = null;
   
   public boolean checkIp(String ip) {
if (!this.geoliteEnabled) {
return true;
     }
     try {
File file = new File(this.geolitePath + ".mmdb");
if (!file.exists()) {
download();
       }
if (this.reader == null) this.reader = new DatabaseReader.Builder(file).build(); 
InetAddress ipAddress = InetAddress.getByName(ip);
CountryResponse response = this.reader.country(ipAddress);
return response.getCountry().getIsoCode().equals(this.geoliteCountry);
} catch (Exception e) {
logger.error("Error checking IP", e);
return false;
     } 
   }
   
   @Scheduled(cron = "0 30 10 * * 3")
   private void download() {
if (!this.geoliteEnabled) {
       return;
     }
     try {
File file = new File(this.geolitePath + ".mmdb");
 
       
if (file.exists()) {
file.delete();
       }
       
logger.info("Downloading GeoLite2-Country.mmdb.");
       
File directory = new File(this.geolitePath);
directory.mkdirs();
 
       
HttpRequest request = HttpRequest.newBuilder()
.uri(URI.create(this.geoliteUrl))
.build();
HttpResponse<byte[]> response = (HttpResponse)this.client.send(request, (HttpResponse.BodyHandler)HttpResponse.BodyHandlers.ofByteArray());
FileOutputStream fos = new FileOutputStream(this.geolitePath + ".tar.gz"); 
try { fos.write(response.body());
fos.close(); } catch (Throwable throwable) { try { fos.close(); }
         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
          throw throwable; }
ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "tar", "-xzf", this.geolitePath + ".tar.gz", "-C", this.geolitePath });
processBuilder.start().waitFor();
 
       
Path geoliteDirPath = Paths.get(this.geolitePath, new String[0]);
List<Path> subdirectories = (List<Path>)Files.list(geoliteDirPath)
.filter(x$0 -> Files.isDirectory(x$0, new java.nio.file.LinkOption[0]))
.filter(path -> path.getFileName().toString().startsWith("GeoLite2-Country"))
         .collect(Collectors.toList());
       Path sourceDirectory = subdirectories.get(0);
       Path sourceFilePath = sourceDirectory.resolve("GeoLite2-Country.mmdb");
 
       
       processBuilder = new ProcessBuilder(new String[] { "mv", sourceFilePath.toString(), this.geolitePath + ".mmdb" });
       processBuilder.start().waitFor();
 
       
       new File(this.geolitePath + ".tar.gz").delete();
 
       
       processBuilder = new ProcessBuilder(new String[] { "rm", "-r", this.geolitePath });
       processBuilder.start().waitFor();
       
       this.reader = null;
       logger.info("Downloaded GeoLite2-Country.mmdb.");
     } catch (Exception e) {
       logger.error("Error downloading GeoLite2-Country.mmdb", e);
     } 
   }
 }


