package br.com.api.timesheet.unit.service;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.findByUsername(username);
        } catch (BusinessException e) {
            throw new UsernameNotFoundException(username);
        }

        return new UserSpringSecurity(user.getUsername(), user.getPassword(), user.getName(), user.getProfile());
    }
}
