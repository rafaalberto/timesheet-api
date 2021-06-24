package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BonusRepository extends JpaRepository<Bonus, Long> {

  @Query("select bonus FROM Bonus bonus " +
          "where bonus.employee.id = :employee " +
          "and bonus.yearReference = :year " +
          "and bonus.monthReference = :month " +
          "order by bonus.description ASC")
  @Transactional(readOnly = true)
  List<Bonus> findByEmployeeAndPeriod(@Param("employee") Long employee, @Param("year") Integer year, @Param("month") Integer month);
}

