package br.com.api.timesheet.resource.position;

import lombok.Builder;

import java.util.Optional;

@Builder
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

}
