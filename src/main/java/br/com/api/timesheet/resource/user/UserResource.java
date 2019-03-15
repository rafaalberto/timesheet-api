package br.com.api.timesheet.resource.user;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.enumeration.ProfileEnum;
import br.com.api.timesheet.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class UserResource {

    private UserService userService;

    public UserResource(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<Page<User>> findAll(
            @RequestParam(value = "page", required = false) final Integer page,
            @RequestParam(value = "size", required = false) final Integer size,
            @RequestParam(value = "username", required = false) final String username,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "type", required = false) final String profile) {

        final UserRequest userRequest = UserRequest.Builder.builder()
                .withPage(page)
                .withSize(size)
                .withUsername(username)
                .withName(name)
                .withProfile(StringUtils.isNotBlank(profile) ? ProfileEnum.valueOf(profile) : null)
                .build();

        Page<User> users = userService.findAll(userRequest);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User user) {
        user.setId(id);
        return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
