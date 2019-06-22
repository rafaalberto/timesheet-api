package br.com.api.timesheet.repository;

import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.TimesheetRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface TimesheetRegisterRepository extends JpaRepository<TimesheetRegister, Long> {

    @Query("select new br.com.api.timesheet.dto.TimesheetReport (reg.type, sum(reg.hoursWorked), sum(reg.hoursJourney), " +
            "sum(reg.weeklyRest), sum(reg.extraHours), sum(reg.sumula90), " +
            "sum(reg.nightShift), sum(reg.paidNightTime)) " +
            "from TimesheetRegister reg " +
            "where reg.employee.id = :employee " +
            "and reg.yearReference = :year " +
            "and reg.monthReference = :month " +
            "group by reg.type ")
    @Transactional(readOnly = true)
    Collection<TimesheetReport> listReport(@Param("employee") Long employee, @Param("year") Integer year, @Param("month") Integer month);

    @Query("select reg FROM TimesheetRegister reg " +
            "where reg.employee.id = :employee " +
            "and reg.yearReference = :year " +
            "and reg.monthReference = :month " +
            "order by timeIn desc")
    @Transactional(readOnly = true)
    List<TimesheetRegister> findByEmployeeAndPeriod(@Param("employee") Long employee, @Param("year") Integer year, @Param("month") Integer month);
}
