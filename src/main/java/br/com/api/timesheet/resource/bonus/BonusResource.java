package br.com.api.timesheet.resource.bonus;

import br.com.api.timesheet.entity.Bonus;
import br.com.api.timesheet.service.BonusService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
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
  public ResponseEntity<Bonus> update(@PathVariable Long id,
      @Valid @RequestBody BonusRequest request) {
    request.setId(id);
    return new ResponseEntity<>(bonusService.save(request), HttpStatus.OK);
  }

  @DeleteMapping("/bonus/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    bonusService.delete(id);
  }

}
