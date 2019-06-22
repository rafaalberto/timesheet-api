package br.com.api.timesheet.resource;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserResourceTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("rafaalberto");
    }

    @Test
    @WithMockUser(username = "rafaalberto", roles = {"ADMIN"})
    public void shouldReturnValidUserData() throws Exception {
        BDDMockito.given(userService.findById(Mockito.anyLong())).willReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("rafaalberto"));
    }

    @Test
    @WithMockUser(username = "rafaalberto", roles = {"ADMIN"})
    public void shouldReturnUserNotFound() throws Exception {
        BDDMockito.given(userService.findById(Mockito.anyLong())).willThrow(new BusinessException("error-user-9", HttpStatus.BAD_REQUEST));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].code").value("error-user-9"));
    }

}
