package br.com.api.timesheet.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface BCryptUtil {

  /**
   * Encode.
   * @param password - password
   * @return
   */
  static String encode(String password) {
    BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
    if (password != null) {
      password = bcryptEncoder.encode(password);
    }
    return password;
  }

}
