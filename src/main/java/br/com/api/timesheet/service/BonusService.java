package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Bonus;
import br.com.api.timesheet.resource.bonus.BonusRequest;

import java.util.List;

public interface BonusService {
    List<Bonus> findByEmployeeAndPeriod(Long employee, Integer year, Integer month);
    Bonus findById(Long id);
    Bonus save(BonusRequest request);
    void delete(Long id);
}
