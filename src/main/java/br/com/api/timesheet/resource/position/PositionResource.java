package br.com.api.timesheet.resource.position;

import br.com.api.timesheet.dto.OfficeHours;
import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.enumeration.OfficeHoursEnum;
import br.com.api.timesheet.enumeration.PeriodEnum;
import br.com.api.timesheet.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class PositionResource {

    private PositionService positionService;

    public PositionResource(@Autowired PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/positions")
    public ResponseEntity<Page<Position>> findAll(
            @RequestParam(value = "page", required = false) final Integer page,
            @RequestParam(value = "size", required = false) final Integer size,
            @RequestParam(value = "title", required = false) final String title) {

        final PositionRequest positionRequest = PositionRequest.builder()
                .page(page)
                .size(size)
                .title(title)
                .build();

        Page<Position> positions = positionService.findAll(positionRequest);
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/positions/{id}")
    public ResponseEntity<Position> findById(@PathVariable Long id) {
        log.info("findById request");
        Position position = positionService.findById(id);
        return ResponseEntity.ok(position);
    }

    @PostMapping(value = "/positions", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Position> create(@Valid @RequestBody PositionRequest positionRequest) {
        return new ResponseEntity<>(positionService.save(positionRequest), HttpStatus.CREATED);
    }

    @PutMapping("/positions/{id}")
    public ResponseEntity<Position> update(@PathVariable Long id, @Valid @RequestBody PositionRequest positionRequest) {
        positionRequest.setId(id);
        return new ResponseEntity<>(positionService.save(positionRequest), HttpStatus.OK);
    }

    @DeleteMapping("/positions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        positionService.delete(id);
    }

    @GetMapping("/positions/officeHours/{period}")
    public ResponseEntity<List<OfficeHours>> findByPeriod(@PathVariable String period) {
        return ResponseEntity.ok(OfficeHoursEnum.fetchByPeriod(PeriodEnum.valueOf(period)));
    }
}
