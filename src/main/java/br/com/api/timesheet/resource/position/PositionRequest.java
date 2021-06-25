package br.com.api.timesheet.resource.position;

import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionRequest {

  private Integer page;

  private Integer size;

  private Long id;

  @NotBlank(message = "error-position-1")
  @Size(min = 3, max = 50, message = "error-position-2")
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
