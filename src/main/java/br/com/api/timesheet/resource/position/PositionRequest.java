package br.com.api.timesheet.resource.position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class PositionRequest {

    private Integer page;
    private Integer size;
    private Long id;
    private String title;
    private boolean dangerousness;

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
