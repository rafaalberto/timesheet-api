package br.com.api.timesheet.bdd.resource;

import br.com.api.timesheet.bdd.http.HttpRequestStepDefinitions;
import br.com.api.timesheet.entity.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Ignore;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Ignore
public class UserStepDefinitions extends HttpRequestStepDefinitions {

  private Long userId;

  private static JsonObject buildRequest(String username) {
    final JsonObject request = new JsonObject();
    request.addProperty("username", username);
    request.addProperty("password", "123456");
    request.addProperty("name", "Rafael");
    request.addProperty("profile", "ROLE_ADMIN");
    return request;
  }

  @When("I request to create a user")
  public void i_request_to_create_a_user() throws Exception {
    mvcPerform(post("/users")
            .contentType(APPLICATION_JSON)
            .content(gson.toJson(buildRequest("rafaalberto"))));
  }

  @Then("I should see a user created successfully")
  public void i_should_see_a_user_created_successfully() throws UnsupportedEncodingException {
    String response = getResultActions().andReturn().getResponse().getContentAsString();
    final User userResponse = gson.fromJson(response, User.class);
    assertThat(userResponse.getUsername()).isEqualTo("rafaalberto");
  }

  @Given("I have a user stored with username {string}")
  public void i_have_a_user_stored_with_username(String username) throws Exception {
    mvcPerform(get("/users?username=" + username + "&profile=ROLE_ADMIN&name=Rafael")
            .contentType(APPLICATION_JSON));
    final List<User> users = getUsersResponse();
    userId = users.get(0).getId();
  }

  @When("I request to update this user with username to {string}")
  public void i_request_to_update_this_user_with_username_to(String username) throws Exception {
    mvcPerform(put("/users/" + userId)
            .contentType(APPLICATION_JSON)
            .content(gson.toJson(buildRequest(username))));
  }

  @Then("I should see user updated successfully")
  public void i_should_see_user_updated_successfully() throws Exception {
    mvcPerform(get("/users?id=" + userId)
            .contentType(APPLICATION_JSON));
    final List<User> users = getUsersResponse();
    assertThat(users.get(0).getUsername()).isEqualTo("usertest");
  }

  @When("I request to delete this user")
  public void i_request_to_delete_this_user_with_username_to() throws Exception {
    mvcPerform(delete("/users/" + userId)
            .contentType(APPLICATION_JSON));
  }

  @Then("I should see a user deleted successfully")
  public void i_should_see_a_user_deleted_successfully() throws Exception {
    mvcPerform(get("/users?id=" + userId)
            .contentType(APPLICATION_JSON));
    final List<User> users = getUsersResponse();
    assertTrue(users.isEmpty());
  }

  private List<User> getUsersResponse() throws UnsupportedEncodingException {
    String response = getResultActions().andReturn().getResponse().getContentAsString();
    JsonElement usersElement = new JsonParser().parse(response).getAsJsonObject().get("content");
    return Arrays.asList(gson.fromJson(usersElement, User[].class));
  }
}
