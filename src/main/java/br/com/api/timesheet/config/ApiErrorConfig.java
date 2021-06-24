package br.com.api.timesheet.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@java.lang.SuppressWarnings("squid:S1118")
@Configuration
public class ApiErrorConfig {

  /**
   * Message configuration.
   * @return
   */

  @Bean
  public static MessageSource apiErrorMessageSource() {
    ReloadableResourceBundleMessageSource messageSource =
            new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:/api-errors");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

}
