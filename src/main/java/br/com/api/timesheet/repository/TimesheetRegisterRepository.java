package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.TimesheetRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetRegisterRepository extends JpaRepository<TimesheetRegister, Long> {
}
