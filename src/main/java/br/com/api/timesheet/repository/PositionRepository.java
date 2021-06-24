package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Position;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PositionRepository extends CrudRepository<Position, Long>, JpaSpecificationExecutor<Position> {

  @Transactional(readOnly = true)
  Optional<Position> findByTitle(String title);
}

