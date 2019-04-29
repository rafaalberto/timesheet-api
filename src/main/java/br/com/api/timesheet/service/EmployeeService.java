package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.EmployeeCustomizedQueries;
import br.com.api.timesheet.repository.EmployeeRepository;
import br.com.api.timesheet.resource.employee.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeCustomizedQueries employeeCustomizedQueries;

    private CompanyService companyService;

    private PositionService positionService;

    public EmployeeService(@Autowired EmployeeRepository employeeRepository,
                           @Autowired EmployeeCustomizedQueries employeeCustomizedQueries,
                           @Autowired CompanyService companyService,
                           @Autowired PositionService positionService ) {
        this.employeeRepository = employeeRepository;
        this.employeeCustomizedQueries = employeeCustomizedQueries;
        this.companyService = companyService;
        this.positionService = positionService;
    }

    public Page<Employee> findAll(EmployeeRequest employeeRequest) {
        return employeeCustomizedQueries.findAll(employeeRequest);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("error-employee-9", HttpStatus.BAD_REQUEST));
    }

    public Employee save(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employeeRequest.getId().ifPresent(id -> employee.setId(id));
        employee.setName(employeeRequest.getName().get());
        employee.setRecordNumber(employeeRequest.getRecordNumber().get());
        employee.setCostCenter(employeeRequest.getCostCenter().get());
        employee.setCostHour(employeeRequest.getCostHour().get());
        employee.setCompany(companyService.findById(employeeRequest.getCompanyId().get()));
        employee.setPosition(positionService.findById(employeeRequest.getPositionId().get()));
        verifyIfEmployeeExist(employee);
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        employeeRepository.delete(findById(id));
    }
    
    private void verifyIfEmployeeExist(final Employee employee) {
        Optional<Employee> employeeDB = employeeRepository.findByRecordNumber(employee.getRecordNumber());
        if (employeeDB.isPresent() && employeeDB.get().getId() != employee.getId()) {
            throw new BusinessException("error-employee-8", HttpStatus.BAD_REQUEST);
        }
    }
}
