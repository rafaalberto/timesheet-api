package br.com.api.timesheet;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "br.com.api.timesheet.cucumber",
        plugin = {"pretty"},
//        tags = {"@test"},
        features = "src/test/resources")
public class TimesheetApplicationTests {

}