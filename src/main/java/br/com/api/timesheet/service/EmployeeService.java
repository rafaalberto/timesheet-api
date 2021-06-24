package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.resource.employee.EmployeeRequest;
import org.springframework.data.domain.Page;

public interface EmployeeService {
  Page<Employee> findAll(EmployeeRequest request);

  Employee findById(Long id);

  Employee save(EmployeeRequest employeeRequest);

  void delete(Long id);
}
