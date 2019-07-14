package br.com.api.timesheet.resource.company;

import br.com.api.timesheet.entity.Company;
import br.com.api.timesheet.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class CompanyResource {

    private CompanyService companyService;

    public CompanyResource(@Autowired CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public ResponseEntity<Page<Company>> findAll(
            @RequestParam(value = "page", required = false) final Integer page,
            @RequestParam(value = "size", required = false) final Integer size,
            @RequestParam(value = "document", required = false) final String document,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "profile", required = false) final String profile) {

        final CompanyRequest companyRequest = CompanyRequest.Builder.builder()
                .withPage(page)
                .withSize(size)
                .withDocument(document)
                .withName(name)
                .build();

        Page<Company> companies = companyService.findAll(companyRequest);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Company> findById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Company> create(@Valid @RequestBody Company company) {
        return new ResponseEntity<>(companyService.save(company), HttpStatus.CREATED);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @Valid @RequestBody Company company) {
        company.setId(id);
        return new ResponseEntity<>(companyService.save(company), HttpStatus.OK);
    }

    @DeleteMapping("/companies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        companyService.delete(id);
    }

}
