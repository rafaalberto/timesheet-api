package br.com.api.timesheet.resource.bonus;

import br.com.api.timesheet.entity.Bonus;
import br.com.api.timesheet.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BonusResource {

    @Autowired
    private BonusService bonusService;

    public BonusResource(@Autowired BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @GetMapping("/bonus/{employee}/{year}/{month}")
    public ResponseEntity<List<Bonus>> findByEmployeeAndPeriod(
            @PathVariable Long employee, @PathVariable Integer year, @PathVariable Integer month) {
        List<Bonus> bonuses = bonusService.findByEmployeeAndPeriod(employee, year, month);
        return ResponseEntity.ok(bonuses);
    }

    @PostMapping("/bonus")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Bonus> create(@Valid @RequestBody BonusRequest request) {
        return new ResponseEntity<>(bonusService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/bonus/{id}")
    public ResponseEntity<Bonus> update(@PathVariable Long id, @Valid @RequestBody BonusRequest request) {
        request.setId(id);
        return new ResponseEntity<>(bonusService.save(request), HttpStatus.OK);
    }

    @DeleteMapping("/bonus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bonusService.delete(id);
    }

}
