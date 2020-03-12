package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.enumeration.OfficeHoursEnum;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.EmployeeRepository;
import br.com.api.timesheet.repository.EmployeeRepositorySpecification;
import br.com.api.timesheet.resource.employee.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.api.timesheet.utils.Constants.DEFAULT_PAGE;
import static br.com.api.timesheet.utils.Constants.DEFAULT_SIZE;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private CompanyService companyService;

    private PositionService positionService;

    public EmployeeService(@Autowired EmployeeRepository employeeRepository,
                           @Autowired CompanyService companyService,
                           @Autowired PositionService positionService ) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
        this.positionService = positionService;
    }

    public Page<Employee> findAll(EmployeeRequest request) {
        final Pageable pageable = PageRequest.of(request.getPage().orElse(DEFAULT_PAGE), request.getSize().orElse(DEFAULT_SIZE));
        return employeeRepository.findAll(EmployeeRepositorySpecification.criteriaSpecification(request), pageable);
    }

    public Employee findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("error-employee-9", HttpStatus.BAD_REQUEST));
        employee.setOfficeHourDescription(employee.getOfficeHour().getDescription());
        return employee;
    }

    public Employee save(Employee employee) {
        verifyIfEmployeeExist(employee);
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        employeeRepository.delete(findById(id));
    }
    
    private void verifyIfEmployeeExist(final Employee employee) {
        Optional<Employee> employeeDB = employeeRepository.findByRecordNumber(employee.getRecordNumber());
        if (employeeDB.isPresent() && !employeeDB.get().getId().equals(employee.getId())) {
            throw new BusinessException("error-employee-8", HttpStatus.BAD_REQUEST);
        }
    }
}
