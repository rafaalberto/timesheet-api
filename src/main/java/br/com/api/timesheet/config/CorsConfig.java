package br.com.api.timesheet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    public static final String PATH_PATTERN = "/**";
    public static final String CORS_ALLOWED_URLS = "cors.allowed.urls";
    public static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "HEAD"};

    @Autowired
    private Environment environment;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(PATH_PATTERN)
                .allowedOrigins(environment.getProperty(CORS_ALLOWED_URLS))
                .allowedMethods(ALLOWED_METHODS)
                .allowCredentials(true);
    }

}
