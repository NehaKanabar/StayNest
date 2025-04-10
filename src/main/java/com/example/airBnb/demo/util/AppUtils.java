package com.example.airBnb.demo.util;

import com.example.airBnb.demo.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {
    public static User getCurrentUser()
    {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
