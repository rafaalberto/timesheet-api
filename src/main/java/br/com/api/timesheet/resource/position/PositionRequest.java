package br.com.api.timesheet.resource.position;

import java.util.Optional;

public class PositionRequest {

    private Integer page;
    private Integer size;
    private String title;

    public Optional<Integer> getPage() {
        return Optional.ofNullable(page);
    }

    public Optional<Integer> getSize() {
        return Optional.ofNullable(size);
    }

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public static final class Builder {
        private Integer page;
        private Integer size;
        private String title;

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

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public PositionRequest build() {
            PositionRequest positionRequest = new PositionRequest();
            positionRequest.page = this.page;
            positionRequest.size = this.size;
            positionRequest.title = this.title;
            return positionRequest;
        }
    }
}
