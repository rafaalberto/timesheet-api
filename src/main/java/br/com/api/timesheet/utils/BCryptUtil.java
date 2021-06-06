package br.com.api.timesheet.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface BCryptUtil {

    static String encode(String password) {
        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
        if(password != null){
            password = bCryptEncoder.encode(password);
        }
        return password;
    }

}
