package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.enumeration.ProfileEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("rafaalberto");
        user.setPassword("123456");
        user.setName("Rafael");
        user.setProfile(ProfileEnum.ROLE_ADMIN);
    }

    @Test
    public void shouldCreateUser() {
        User userSaved = userRepository.save(user);
        assertThat(userSaved.getId()).isNotNull();
        assertThat(userSaved.getUsername()).isEqualTo("rafaalberto");
        assertThat(userSaved.getPassword()).isEqualTo("123456");
    }

    @Test
    public void findAll() {
        userRepository.save(user);
        Page<User> users = userRepository.findAll(PageRequest.of(0, 5));
        assertThat(users.getNumberOfElements()).isEqualTo(BigInteger.ONE.intValue());
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

}
