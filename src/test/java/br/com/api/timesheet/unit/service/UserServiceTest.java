package br.com.api.timesheet.unit.service;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.enumeration.ProfileEnum;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.UserRepository;
import br.com.api.timesheet.resource.user.UserRequest;
import br.com.api.timesheet.service.impl.UserServiceImpl;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Captor
  private ArgumentCaptor<User> userArgumentCaptor;

  private UserRequest userRequest;

  private User user;

  @Before
  public void setUp() {
    userRequest = getUserRequestBuilder();
    user = getUserBuilder();
  }

  @Test
  public void shouldFindAll() {
    Page<User> usersPage = new PageImpl<>(singletonList(user), of(0, 10), 1);
    when(userRepository.findAll(any(Specification.class), any(PageRequest.class)))
            .thenReturn(usersPage);

    Page<User> users = userService.findAll(userRequest);

    verify(userRepository, times(1))
            .findAll(any(Specification.class), any(PageRequest.class));

    assertThat(users.getTotalElements()).isEqualTo(1);
  }

  @Test
  public void shouldSave() {
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(user);

    userService.save(userRequest);

    verify(userRepository, times(1)).findByUsername(anyString());
    verify(userRepository, times(1)).save(userArgumentCaptor.capture());

    assertThat(userArgumentCaptor.getValue().getName()).isEqualTo("Rafael");
  }

  @Test
  public void shouldUpdate() {
    User userUpdated = user;
    userUpdated.setName("Rafa");

    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(userUpdated);

    userService.save(userRequest);

    verify(userRepository, times(1)).findByUsername(anyString());
    verify(userRepository, times(1)).save(userArgumentCaptor.capture());

    assertThat(userRequest.getName().get()).contains("Rafael");
    assertThat(user.getName()).isEqualTo("Rafa");
  }

  @Test
  public void shouldFindById() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    User userFound = userService.findById(anyLong());
    verify(userRepository, times(1)).findById(anyLong());

    assertThat(userFound.getId()).isEqualTo(1);
  }

  @Test(expected = BusinessException.class)
  public void shouldThrowWhenUserDoesNotExists() {
    when(userRepository.findById(anyLong())).thenThrow(
            new BusinessException("error-user-9", BAD_REQUEST));

    userService.findById(1L);

    verify(userRepository, times(1)).findById(anyLong());
  }

  @Test(expected = BusinessException.class)
  public void shouldThrowWhenUserAlreadyExists() {
    User userFound = user;
    userFound.setId(2L);

    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userFound));

    userService.save(userRequest);

    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void shouldDelete() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    userService.delete(1L);

    verify(userRepository, times(1)).delete(any(User.class));
  }

  @Test
  public void shouldFindByUsername() {
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

    User userFound = userService.findByUsername("rafaalberto");
    verify(userRepository, times(1)).findByUsername(anyString());

    assertThat(userFound.getUsername()).isEqualTo("rafaalberto");
  }

  private UserRequest getUserRequestBuilder() {
    return UserRequest.builder()
            .id(1L)
            .username("rafaalberto")
            .name("Rafael")
            .profile(ProfileEnum.ROLE_ADMIN)
            .password("123456")
            .build();
  }

  private User getUserBuilder() {
    return User.builder()
            .id(1L)
            .username("rafaalberto")
            .name("Rafael")
            .profile(ProfileEnum.ROLE_ADMIN)
            .password("123456")
            .build();
  }

}
