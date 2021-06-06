package br.com.api.timesheet.unit.service;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.enumeration.ProfileEnum;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.unit.repository.UserRepository;
import br.com.api.timesheet.unit.resource.UserRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private UserRequest userRequest;

    private User userDB;

    @Before
    public void setUp() {
        userRequest = UserRequest.builder()
                .id(1L)
                .username("rafaalberto")
                .name("Rafael")
                .profile(ProfileEnum.ROLE_ADMIN)
                .password("123")
                .build();
        userDB = User.builder().id(1L)
                .username("rafaalberto")
                .name("Rafael")
                .profile(ProfileEnum.ROLE_ADMIN)
                .password("123").build();
    }

    @Test
    public void shouldFindAll() {
        Page<User> usersPage = new PageImpl<>(Collections.singletonList(userDB), PageRequest.of(0, 10), 1);
        UserRequest request = UserRequest.builder().build();
        Mockito.when(userRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class))).thenReturn(usersPage);
        Page<User> users = userService.findAll(request);
        Mockito.verify(userRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class));
        Assertions.assertThat(users.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void shouldSave() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userDB);
        userService.save(userRequest);
        Mockito.verify(userRepository, Mockito.times(1)).save(userArgumentCaptor.capture());
        Assertions.assertThat(userArgumentCaptor.getValue().getId()).isEqualTo(1);
    }

    @Test
    public void shouldUpdate() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(User.builder().id(1L).username("josesilva").build()));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userDB);
        userService.save(userRequest);
        Mockito.verify(userRepository, Mockito.times(1)).save(userArgumentCaptor.capture());
        Assertions.assertThat(userArgumentCaptor.getValue().getId()).isEqualTo(1);
    }

    @Test
    public void shouldFindById() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userDB));
        User userFound = userService.findById(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Assertions.assertThat(userFound.getId()).isEqualTo(1);
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowWhenUserDoesNotExists() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new BusinessException("error-user-9", HttpStatus.BAD_REQUEST));
        userService.findById(1L);
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test(expected = BusinessException.class)
    public void shouldThrowWhenUserAlreadyExists() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(User.builder().id(3L).username("rafaalberto").name("José").build()));
        userService.save(userRequest);
        Mockito.verify(userRepository, Mockito.times(1)).save(userDB);
    }

    @Test
    public void shouldDelete() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(userDB));
        userService.delete(1L);
        Mockito.verify(userRepository, Mockito.times(1)).delete(Mockito.any(User.class));
    }

    @Test
    public void shouldFindByUsername() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(userDB));
        User userFound = userService.findByUsername("rafaalberto");
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.anyString());
        Assertions.assertThat(userFound.getUsername()).isEqualTo("rafaalberto");
    }

}
