package br.com.api.timesheet.bdd.resource;

import br.com.api.timesheet.bdd.DatabaseActions;
import br.com.api.timesheet.bdd.http.HttpRequestStepDefinitions;
import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.resource.position.PositionRequest;
import com.google.gson.JsonObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Ignore
public class PositionStepDefinitions extends HttpRequestStepDefinitions {

    @Autowired
    private final DatabaseActions databaseActions;

    public PositionStepDefinitions(DatabaseActions databaseActions) {
        this.databaseActions = databaseActions;
    }

    @Before
    public void setUp() {
        databaseActions.clear();
    }

    @Transactional
    @Given("I already have stored these positions")
    public void i_already_have_stored_these_positions(final DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        data.forEach(row -> {
            Position position = Position.builder()
                    .id(Long.valueOf(row.get("Id")))
                    .title(row.get("Title")).build();
            databaseActions.insertPosition(position);
        });
    }

    @When("I request to create a position with data")
    public void i_request_to_create_a_position_with_data(final DataTable dataTable) throws Throwable {
        mvcPerform(post("/positions")
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(buildRequest(getPosition(dataTable)))));
    }

    @When("I request to update a position with id {string} and data")
    public void i_request_to_update_a_position_with_data(final String id, final DataTable dataTable) throws Throwable {
        mvcPerform(put("/positions/" + id)
                .contentType(APPLICATION_JSON)
                .content(gson.toJson(buildRequest(getPosition(dataTable)))));
    }

    @When("I request to delete a position with id {string}")
    public void i_request_to_delete_a_position_with_id(final String id) throws Throwable {
        mvcPerform(delete("/positions/" + id));
    }

    @Then("I should receive status {string}")
    public void i_should_receive_status(final String status) throws Throwable {
        super.http_status_must_be(status);
    }

    @Then("I should receive message {string}")
    public void i_should_receive_message(final String message) throws Throwable {
        assertTrue(getResultActions().andReturn().getResponse().getContentAsString().contains(message));
    }

    private PositionRequest getPosition(final DataTable dataTable) {
        AtomicReference<PositionRequest> position = new AtomicReference<>(new PositionRequest());
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        data.forEach(row ->
                position.set(PositionRequest.builder().title(row.get("Title")).dangerousness(false).build())
        );
        return position.get();
    }

    private static JsonObject buildRequest(final PositionRequest position) {
        final JsonObject request = new JsonObject();
        request.addProperty("title", position.getTitle().orElse(""));
        request.addProperty("dangerousness", position.isDangerousness());
        return request;
    }

}
