package br.com.api.timesheet.cucumber.resource;

import br.com.api.timesheet.cucumber.http.HttpRequestStepDefinitions;
import com.google.gson.JsonObject;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Ignore;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Ignore
public class PositionStepDefinitions extends HttpRequestStepDefinitions {

    @When("I post position data")
    public void i_post_position_data() throws Throwable {
        mvcPerform(post("/positions")
        .contentType(APPLICATION_JSON)
        .content(gson.toJson(buildRequest())));
    }

    @Then("I should to receive status {string}")
    public void i_should_to_receive_status(final String status) throws Throwable {
        super.http_status_must_be(status);
    }

    private static JsonObject buildRequest() {
        final JsonObject request = new JsonObject();
        request.addProperty("title", "New Position");
        return request;
    }


}
