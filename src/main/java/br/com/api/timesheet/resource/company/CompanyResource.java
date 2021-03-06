package br.com.api.timesheet.resource.company;

import br.com.api.timesheet.entity.Company;
import br.com.api.timesheet.service.CompanyService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class CompanyResource {

  private CompanyService companyService;

  public CompanyResource(@Autowired CompanyService companyService) {
    this.companyService = companyService;
  }

  /**
   * Return all companies.
   * @param page - page
   * @param size - size
   * @param document - document
   * @param name - name
   * @return
   */
  @GetMapping("/companies")
  public ResponseEntity<Page<Company>> findAll(
          @RequestParam(value = "page", required = false) final Integer page,
          @RequestParam(value = "size", required = false) final Integer size,
          @RequestParam(value = "document", required = false) final String document,
          @RequestParam(value = "name", required = false) final String name) {

    final CompanyRequest companyRequest = CompanyRequest.builder()
            .page(page)
            .size(size)
            .document(document)
            .name(name)
            .build();

    Page<Company> companies = companyService.findAll(companyRequest);
    return ResponseEntity.ok(companies);
  }

  @GetMapping("/companies/{id}")
  public ResponseEntity<Company> findById(@PathVariable Long id) {
    Company company = companyService.findById(id);
    return ResponseEntity.ok(company);
  }

  @PostMapping("/companies")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Company> create(@Valid @RequestBody CompanyRequest companyRequest) {
    return new ResponseEntity<>(companyService.save(companyRequest), HttpStatus.CREATED);
  }

  @PutMapping("/companies/{id}")
  public ResponseEntity<Company> update(@PathVariable Long id,
      @Valid @RequestBody CompanyRequest companyRequest) {
    companyRequest.setId(id);
    return new ResponseEntity<>(companyService.save(companyRequest), HttpStatus.OK);
  }

  @DeleteMapping("/companies/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    companyService.delete(id);
  }

}
