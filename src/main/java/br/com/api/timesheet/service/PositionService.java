package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.resource.position.PositionRequest;
import org.springframework.data.domain.Page;

public interface PositionService {
  Page<Position> findAll(PositionRequest request);

  Position findById(Long id);

  Position save(PositionRequest positionRequest);

  void delete(Long id);
}
