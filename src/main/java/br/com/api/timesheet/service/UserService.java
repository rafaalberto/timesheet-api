package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.resource.user.UserRequest;
import org.springframework.data.domain.Page;

public interface UserService {
  Page<User> findAll(UserRequest request);

  User findById(Long id);

  User save(UserRequest userRequest);

  void delete(Long id);

  User findByUsername(String username);
}
