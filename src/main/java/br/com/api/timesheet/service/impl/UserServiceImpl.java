package br.com.api.timesheet.service.impl;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.UserRepository;
import br.com.api.timesheet.repository.specification.UserRepositorySpecification;
import br.com.api.timesheet.resource.user.UserRequest;
import br.com.api.timesheet.service.UserService;
import br.com.api.timesheet.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static br.com.api.timesheet.utils.Constants.DEFAULT_PAGE;
import static br.com.api.timesheet.utils.Constants.DEFAULT_SIZE;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findAll(UserRequest request) {
        final Pageable pageable = PageRequest.of(request.getPage().orElse(DEFAULT_PAGE), request.getSize().orElse(DEFAULT_SIZE));
        return userRepository.findAll(UserRepositorySpecification.criteriaSpecification(request), pageable);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("error-user-9", HttpStatus.BAD_REQUEST));
    }

    public User save(UserRequest userRequest) {
        User user = User.builder()
                .id(userRequest.getId())
                .name(userRequest.getName().orElse(""))
                .username(userRequest.getUsername().orElse(""))
                .password(userRequest.getPassword())
                .profile(userRequest.getProfile().orElse(null))
                .build();
        verifyIfUserExist(user);
        user.setPassword(BCryptUtil.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(findById(id));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("error-user-9", HttpStatus.BAD_REQUEST));
    }

    private void verifyIfUserExist(final User user) {
        User userDB = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (userDB != null && !userDB.getId().equals(user.getId())) {
            throw new BusinessException("error-user-8", HttpStatus.BAD_REQUEST);
        }
    }
}
