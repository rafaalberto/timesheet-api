package br.com.api.timesheet.resource.employee;

import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.service.EmployeeService;
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
            @RequestParam(value = "name", required = false) final String name) {

        final EmployeeRequest employeeRequest = EmployeeRequest.Builder.builder()
                .withPage(page)
                .withSize(size)
                .withName(name)
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
    public ResponseEntity<Employee> create(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return new ResponseEntity<Employee>(employeeService.save(employeeRequest), HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest employeeRequest) {
        employeeRequest.setId(id);
        return new ResponseEntity<Employee>(employeeService.save(employeeRequest), HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

}
