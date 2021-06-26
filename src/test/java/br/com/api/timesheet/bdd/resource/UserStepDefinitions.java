package br.com.api.timesheet.bdd.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import br.com.api.timesheet.bdd.http.HttpRequestStepDefinitions;
import br.com.api.timesheet.entity.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import org.junit.Ignore;

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

  /**
   * Request do create user.
   */
  @When("I request to create a user")
  public void requestToCreateUser() throws Exception {
    mvcPerform(post("/users")
            .contentType(APPLICATION_JSON)
            .content(gson.toJson(buildRequest("rafaalberto"))));
  }

  /**
   * Should see user created.
   */
  @Then("I should see a user created successfully")
  public void shouldSeeUserCreatedSuccessfully() throws UnsupportedEncodingException {
    String response = getResultActions().andReturn().getResponse().getContentAsString();
    final User userResponse = gson.fromJson(response, User.class);
    assertThat(userResponse.getUsername()).isEqualTo("rafaalberto");
  }

  /**
   * Find user created with username.
   * @param username - username
   */
  @Given("I have a user stored with username {string}")
  public void haveUserStoredWithUsername(String username) throws Exception {
    mvcPerform(get("/users?username=" + username + "&profile=ROLE_ADMIN&name=Rafael")
            .contentType(APPLICATION_JSON));
    final List<User> users = getUsersResponse();
    userId = users.get(0).getId();
  }

  /**
   * Request to update user.
   * @param username - username
   */
  @When("I request to update this user with username to {string}")
  public void requestToUpdateThisUserWithUsernameTo(String username) throws Exception {
    mvcPerform(put("/users/" + userId)
            .contentType(APPLICATION_JSON)
            .content(gson.toJson(buildRequest(username))));
  }

  /**
   * Should see user updated.
   */
  @Then("I should see user updated successfully")
  public void shouldSeeUserUpdatedSuccessfully() throws Exception {
    mvcPerform(get("/users?id=" + userId)
            .contentType(APPLICATION_JSON));
    final List<User> users = getUsersResponse();
    assertThat(users.get(0).getUsername()).isEqualTo("usertest");
  }

  /**
   * Request to delete.
   */
  @When("I request to delete this user")
  public void requestToDeleteThisUserWithUsernameTo() throws Exception {
    mvcPerform(delete("/users/" + userId)
            .contentType(APPLICATION_JSON));
  }

  /**
   * Should see delete.
   */
  @Then("I should see a user deleted successfully")
  public void shouldSeeUserDeletedSuccessfully() throws Exception {
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
