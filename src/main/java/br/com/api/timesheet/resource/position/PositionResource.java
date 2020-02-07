package br.com.api.timesheet.resource.position;

import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Position> findById(@PathVariable Long id) {
        Position position = positionService.findById(id);
        return ResponseEntity.ok(position);
    }

    @PostMapping("/positions")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Position> create(@Valid @RequestBody Position position) {
        return new ResponseEntity<Position>(positionService.save(position), HttpStatus.CREATED);
    }

    @PutMapping("/positions/{id}")
    public ResponseEntity<Position> update(@PathVariable Long id, @Valid @RequestBody Position position) {
        position.setId(id);
        return new ResponseEntity<Position>(positionService.save(position), HttpStatus.OK);
    }

    @DeleteMapping("/positions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        positionService.delete(id);
    }

}
