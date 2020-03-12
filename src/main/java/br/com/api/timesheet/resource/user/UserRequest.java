package br.com.api.timesheet.resource.user;

import br.com.api.timesheet.enumeration.ProfileEnum;
import lombok.Builder;

import java.util.Optional;

@Builder
public class UserRequest {

    private Integer page;
    private Integer size;
    private String username;
    private String name;
    private ProfileEnum profile;

    public Optional<Integer> getPage() {
        return Optional.ofNullable(page);
    }

    public Optional<Integer> getSize() {
        return Optional.ofNullable(size);
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<ProfileEnum> getProfile() { return Optional.ofNullable(profile); }

}
