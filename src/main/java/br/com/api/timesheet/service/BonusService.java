package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Bonus;
import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.BonusRepository;
import br.com.api.timesheet.resource.bonus.BonusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BonusService {

    private BonusRepository bonusRepository;

    private EmployeeService employeeService;

    public BonusService(@Autowired BonusRepository bonusRepository, @Autowired EmployeeService employeeService) {
        this.bonusRepository = bonusRepository;
        this.employeeService = employeeService;
    }

    public List<Bonus> findByEmployeeAndPeriod(Long employee, Integer year, Integer month) {
        return bonusRepository.findByEmployeeAndPeriod(employee, year, month);
    }

    public Bonus findById(Long id) {
        return bonusRepository.findById(id)
                .orElseThrow(() -> new BusinessException("error-Bonus-9", HttpStatus.BAD_REQUEST));
    }

    public Bonus save(BonusRequest request) {
        Bonus bonus = new Bonus();
        request.getId().ifPresent(bonus::setId);
        Optional<Long> employeeId = request.getEmployeeId();
        if(employeeId.isPresent()) {
            Employee employee = employeeService.findById(employeeId.get());
            bonus.setEmployee(employee);
        }
        bonus.setMonthReference(request.getMonthReference());
        bonus.setYearReference(request.getYearReference());
        bonus.setCode(request.getCode());
        bonus.setDescription(request.getDescription());
        bonus.setCost(request.getCost());
        return bonusRepository.save(bonus);
    }

    public void delete(Long id) {
        bonusRepository.delete(findById(id));
    }
}
