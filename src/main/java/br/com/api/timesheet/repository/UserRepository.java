package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

  @Transactional(readOnly = true)
  Optional<User> findByUsername(String username);
}

