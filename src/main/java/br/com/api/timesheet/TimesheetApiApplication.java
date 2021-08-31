package br.com.api.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class TimesheetApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(TimesheetApiApplication.class, args);
  }

}
