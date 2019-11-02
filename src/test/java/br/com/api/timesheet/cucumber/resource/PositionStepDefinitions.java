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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    @When("I attempt to create a new one")
    public void i_attempt_to_create_a_new_one() throws Exception {
        mvcPerform(post("/positions")
        .contentType(APPLICATION_JSON)
        .content(gson.toJson(buildRequest(getPosition()))))
        .andExpect(jsonPath("$.title").value(position.getTitle()));
    }

    @Then("I should to receive status {string}")
    public void i_should_to_receive_status(final String status) throws Throwable {
        super.http_status_must_be(status);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    private static JsonObject buildRequest(final Position position) {
        final JsonObject request = new JsonObject();
        request.addProperty("title", position.getTitle());
        return request;
    }

}
