package com.crashcourse.SpringBootTraining.SongService.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtil {
    private static Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


    public static String getLoggedInUsername() {
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
