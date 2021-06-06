package br.com.api.timesheet.resource;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.enumeration.ProfileEnum;
import br.com.api.timesheet.service.UserService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    Gson gson;

    @Test
    public void shouldFindAll() throws Exception {
        Page<User> result = new PageImpl(Collections.singletonList(User.builder().username("rafael").profile(ProfileEnum.ROLE_ADMIN).build()), PageRequest.of(0, 10), 1);
        Mockito.when(userService.findAll(Mockito.any(UserRequest.class)))
                .thenReturn(result);

        mockMvc.perform(get("/users?page=0&size=10&profile=ROLE_ADMIN")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindById() throws Exception {
        Mockito.when(userService.findById(Mockito.anyLong()))
                .thenReturn(User.builder().id(1L).username("rafaalberto").build());

        ResultActions resultActions = mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("rafaalberto")));
        System.out.println("resultActions: " + resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void shouldCreate() throws Exception {
        Mockito.when(userService.save(Mockito.any(UserRequest.class)))
                .thenReturn(User.builder().id(1L).username("rafaalberto").build());

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(UserRequest.builder().username("rafaalberto").build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("rafaalberto")));
    }

    @Test
    public void shouldUpdate() throws Exception {
        Mockito.when(userService.save(Mockito.any(UserRequest.class)))
                .thenReturn(User.builder().id(1L).username("rafael").build());

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(UserRequest.builder().username("rafael").build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("rafael")));
    }

    @Test
    public void shouldDelete() throws Exception {
        Mockito.doNothing().when(userService).delete(Mockito.anyLong());

        mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
