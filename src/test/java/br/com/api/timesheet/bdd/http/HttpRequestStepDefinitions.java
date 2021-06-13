package br.com.api.timesheet.bdd.http;

import br.com.api.timesheet.bdd.CucumberConfig;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class HttpRequestStepDefinitions extends CucumberConfig {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected Gson gson;

    private ResultActions resultActions;

    public ResultActions mvcPerform(MockHttpServletRequestBuilder request) throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        setResultActions(mockMvc.perform(request));
        return resultActions;
    }

    public void setResultActions(ResultActions resultActions) {
        this.resultActions = resultActions;
    }

    public ResultActions getResultActions() {
        return resultActions;
    }
}
