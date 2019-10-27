package br.com.api.timesheet.cucumber.http;

import br.com.api.timesheet.cucumber.CucumberConfig;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class HttpRequestStepDefinitions extends CucumberConfig {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected Gson gson;

    private ResultActions resultActions;

    public ResultActions mvcPerform(MockHttpServletRequestBuilder request) throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(getWebApplicationContext()).build();
        this.resultActions = mockMvc.perform(request);
        return resultActions;
    }

    public void http_status_must_be(final String httpStatus) throws Throwable {
        resultActions.andExpect(status().is(Integer.valueOf(httpStatus)));
    }

    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    public WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }
}
