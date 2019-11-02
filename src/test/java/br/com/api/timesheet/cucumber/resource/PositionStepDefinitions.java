package br.com.api.timesheet.cucumber.resource;

import br.com.api.timesheet.cucumber.http.HttpRequestStepDefinitions;
import br.com.api.timesheet.entity.Position;
import com.google.gson.JsonObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Ignore;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Ignore
public class PositionStepDefinitions extends HttpRequestStepDefinitions {

    private Position position;

    @Given("the following position data")
    public void the_following_position_data(DataTable dataTable) {
        List<Map<String, String>> positionData = dataTable.asMaps(String.class, String.class);
        positionData.forEach(row -> {
            Position position = new Position();
            position.setTitle(row.get("Title"));
            setPosition(position);
        });
    }

    @When("I attempt to create a new position")
    public void i_attempt_to_create_a_new_position() throws Throwable {
        mvcPerform(post("/positions")
        .contentType(APPLICATION_JSON)
        .content(gson.toJson(buildRequest(getPosition()))));
    }

    @Then("I should receive status {string} and message {string}")
    public void i_should_receive_status_and_message(final String status, final String message) throws Throwable {
        super.http_status_must_be(status);
        assertTrue(getResultActions().andReturn().getResponse().getContentAsString().contains(message));
    }

    private static JsonObject buildRequest(final Position position) {
        final JsonObject request = new JsonObject();
        request.addProperty("title", position.getTitle());
        return request;
    }

    private void setPosition(Position position) {
        this.position = position;
    }

    private Position getPosition() {
        return position;
    }
}
