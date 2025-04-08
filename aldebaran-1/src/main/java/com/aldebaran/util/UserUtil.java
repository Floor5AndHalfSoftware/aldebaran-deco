package com.aldebaran.util;

import com.aldebaran.auth.APrincipal;
import com.aldebaran.auth.SessionPrincipalResolver;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.Principal;


public class UserUtil {
    public static APrincipal getUser(HttpServletRequest request) {
        try {
            Principal principal = SessionPrincipalResolver.getUserPrincipal(request);
            if (principal != null && principal instanceof APrincipal) {
                APrincipal aPrincipal = (APrincipal) principal;
                return aPrincipal;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static boolean isUserSelfexcluded(HttpServletRequest request) {
        APrincipal ap = getUser(request);
        if (ap != null && ap.getStatus() == 2)
            return true;
        return false;
    }

    public static String getEmailUser(HttpServletRequest request) {
        APrincipal p = getUser(request);
        if (p != null)
            return p.getEmail();
        return null;
    }

    public static String getUserToken(HttpServletRequest request) {
        APrincipal p = getUser(request);
        if (p != null)
            return p.getUserId();
        return null;
    }

    public static String getUserId(HttpServletRequest request) {
        APrincipal p = getUser(request);
        if (p != null)
            return "" + p.getId();
        return null;
    }

    public static JsonNode messageUserSelfexcluded() {
        return new ObjectMapper().valueToTree(new com.aldebaran.util.NodeBean(401, "user blocked for selfexclusion"));
    }

    public static String getExternalIpAddress(HttpServletRequest request) throws IOException {
        String internalIp = request.getRemoteAddr();

        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } catch (Exception e) {
            return internalIp;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getLangLiw(String lang) {
        if (lang.equals("en-GB"))
            return "en";
        if (lang.equals("es-ES"))
            return "es";
        return "en";
    }
}


