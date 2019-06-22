package br.com.api.timesheet.resource.user;

import br.com.api.timesheet.enumeration.ProfileEnum;

import java.util.Optional;

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

    public static final class Builder {
        private Integer page;
        private Integer size;
        private String username;
        private String name;
        private ProfileEnum profile;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder withSize(Integer size) {
            this.size = size;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withProfile(ProfileEnum profile) {
            this.profile = profile;
            return this;
        }

        public UserRequest build() {
            UserRequest userRequest = new UserRequest();
            userRequest.username = this.username;
            userRequest.page = this.page;
            userRequest.size = this.size;
            userRequest.name = this.name;
            userRequest.profile = this.profile;
            return userRequest;
        }
    }
}
