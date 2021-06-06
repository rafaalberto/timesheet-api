package br.com.api.timesheet.resource;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.com.api.timesheet.enumeration.ProfileEnum.valueOf;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@CrossOrigin(origins = "http://localhost:8080")
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
            @RequestParam(value = "profile", required = false) final String profile) {

        final UserRequest userRequest = UserRequest.builder()
                .page(page)
                .size(size)
                .username(username)
                .name(name)
                .profile(isNotBlank(profile) ? valueOf(profile) : null)
                .build();

        Page<User> users = userService.findAll(userRequest);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> create(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.save(userRequest), HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        userRequest.setId(id);
        return new ResponseEntity<>(userService.save(userRequest), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
