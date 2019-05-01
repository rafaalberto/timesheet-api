package br.com.api.timesheet.resource.employee;

import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.enumeration.StatusEnum;
import br.com.api.timesheet.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class EmployeeResource {

    private EmployeeService employeeService;

    public EmployeeResource(@Autowired EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<Page<Employee>> findAll(
            @RequestParam(value = "page", required = false) final Integer page,
            @RequestParam(value = "size", required = false) final Integer size,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "status", required = false) final String status) {

        final EmployeeRequest employeeRequest = EmployeeRequest.Builder.builder()
                .withPage(page)
                .withSize(size)
                .withName(name)
                .withStatus(StringUtils.isNotBlank(status) ? StatusEnum.valueOf(status) : null)
                .build();

        Page<Employee> employees = employeeService.findAll(employeeRequest);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        employee.setId(id);
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

}
