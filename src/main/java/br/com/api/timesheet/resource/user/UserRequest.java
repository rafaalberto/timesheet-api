package br.com.api.timesheet.resource.user;

import br.com.api.timesheet.enumeration.ProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

  private Integer page;
  private Integer size;
  private Long id;

  @NotBlank(message = "error-user-1")
  @Size(min = 5, max = 20, message = "error-user-2")
  private String username;

  @NotBlank(message = "error-user-3")
  @Size(min = 5, max = 100, message = "error-user-4")
  private String password;

  @NotBlank(message = "error-user-5")
  @Size(min = 5, max = 50, message = "error-user-6")
  private String name;

  @NotNull(message = "error-user-7")
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

  public Optional<ProfileEnum> getProfile() {
    return Optional.ofNullable(profile);
  }

}
