package br.com.api.timesheet.bdd;

import br.com.api.timesheet.TimesheetApiApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TimesheetApiApplication.class, loader = SpringBootContextLoader.class)
@ActiveProfiles("test")
@Configuration
@SpringBootTest(classes = {TimesheetApiApplication.class, CucumberConfig.class})
@TestPropertySource({"classpath:application-test.properties"})
public abstract class CucumberConfig extends WebMvcConfigurationSupport {

}
