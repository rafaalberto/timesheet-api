package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Transactional(readOnly = true)
    Optional<Employee> findByRecordNumber(String recordNumber);
}
