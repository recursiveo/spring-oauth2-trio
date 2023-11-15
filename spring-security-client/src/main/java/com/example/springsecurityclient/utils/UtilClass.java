package com.example.springsecurityclient.utils;

import jakarta.servlet.http.HttpServletRequest;

public class UtilClass {
    public static String getAppUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() + "/" +
                request.getContextPath();
    }

    public static String getPasswordResetLink(HttpServletRequest request, String token) {
        return getAppUrl(request) +
                "createNewPassword?token=" +
                token;
    }
}
