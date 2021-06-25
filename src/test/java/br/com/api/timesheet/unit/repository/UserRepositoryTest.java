package br.com.api.timesheet.unit.repository;

import static br.com.api.timesheet.enumeration.ProfileEnum.ROLE_ADMIN;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

  private static final String DEFAULT_USERNAME = "rafaalberto";
  User user;
  @Autowired
  private UserRepository userRepository;

  @Before
  public void setUp() {
    user = getUserBuilder();
    userRepository.save(user);
  }

  @Test
  public void shouldFindByUsername() {
    user = userRepository.findByUsername(DEFAULT_USERNAME).orElse(null);
    assert user != null;
    assertThat(user.getUsername()).isEqualTo(DEFAULT_USERNAME);
  }

  @After
  public void tearDown() {
    userRepository.deleteAll();
  }

  private User getUserBuilder() {
    return User.builder()
            .id(1L)
            .username(DEFAULT_USERNAME)
            .name("Rafael")
            .password("123456")
            .profile(ROLE_ADMIN).build();
  }
}
