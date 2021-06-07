package br.com.api.timesheet.resource;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.enumeration.ProfileEnum;
import br.com.api.timesheet.resource.user.UserRequest;
import br.com.api.timesheet.service.UserService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.PageRequest.of;
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
        Page<User> result = new PageImpl(singletonList(getUserBuilder()), of(0, 10), 1);
        when(userService.findAll(any(UserRequest.class))).thenReturn(result);

        mockMvc.perform(get("/users?page=0&size=10&profile=ROLE_ADMIN")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).findAll(any(UserRequest.class));
    }

    @Test
    public void shouldFindById() throws Exception {
        when(userService.findById(anyLong())).thenReturn(getUserBuilder());

        mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("rafaalberto")));

        verify(userService).findById(anyLong());
    }

    @Test
    public void shouldCreate() throws Exception {
        when(userService.save(any(UserRequest.class))).thenReturn(getUserBuilder());

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(getUserBuilder())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("rafaalberto")));

        verify(userService).save(any(UserRequest.class));
    }

    @Test
    public void shouldUpdate() throws Exception {
        User userUpdated = getUserBuilder();
        userUpdated.setUsername("rafael");

        when(userService.save(any(UserRequest.class))).thenReturn(userUpdated);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userUpdated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("rafael")));

        verify(userService).save(any(UserRequest.class));
    }

    @Test
    public void shouldDelete() throws Exception {
        doNothing().when(userService).delete(anyLong());

        mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private User getUserBuilder() {
        return User.builder()
                .id(1L)
                .username("rafaalberto")
                .name("Rafael")
                .profile(ProfileEnum.ROLE_ADMIN)
                .password("12345")
                .build();
    }
}
