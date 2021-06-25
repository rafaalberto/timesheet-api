package br.com.api.timesheet.resource.company;

import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Builder
@Data
public class CompanyRequest {

  private Integer page;
  private Integer size;

  private Long id;

  @CNPJ(message = "error-company-4")
  @NotBlank(message = "error-company-1")
  @Size(max = 18)
  private String document;

  @NotBlank(message = "error-company-2")
  @Size(min = 3, max = 50, message = "error-company-3")
  private String name;

  public Optional<Integer> getPage() {
    return Optional.ofNullable(page);
  }

  public Optional<Integer> getSize() {
    return Optional.ofNullable(size);
  }

  public Optional<String> getDocument() {
    return Optional.ofNullable(document);
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

}
