package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.UserRepository;
import br.com.api.timesheet.resource.user.UserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    private UserService userService;

    private UserRequest userRequest;

    private User userDB;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userRequest = UserRequest.builder().id(1L).build();
        userDB = User.builder().id(1L).username("rafaalberto").build();
        userService = new UserService(userRepository);
    }

    @Test
    public void shouldSave() {
        User user = User.builder().
                id(userRequest.getId())
                .build();
        Mockito.when(userRepository.save(user)).thenReturn(userDB);
        User userSaved = userService.save(userRequest);
        Assert.assertThat(userSaved.getId(), equalTo(1L));
    }

    @Test
    public void shouldFindById() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userDB));
        User userFound = userService.findById(1L);
        Assert.assertThat(userFound.getId(), equalTo(1L));
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowWhenUserDoesNotExists() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userDB));
        userService.findById(2L);
    }

    @Test
    public void shouldDelete() {
        User user = User.builder().
                id(userRequest.getId())
                .build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userDB));
        userService.delete(1L);
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    public void shouldFindByUsername() {
        Mockito.when(userRepository.findByUsername("rafaalberto")).thenReturn(Optional.of(userDB));
        User userFound = userService.findByUsername("rafaalberto");
        Assert.assertThat(userFound.getUsername(), equalTo("rafaalberto"));
    }

}
