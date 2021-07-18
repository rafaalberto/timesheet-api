package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Position;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PositionRepository extends
        CrudRepository<Position, Long>, JpaSpecificationExecutor<Position> {

  @Transactional(readOnly = true)
  Optional<Position> findByTitle(String title);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
  @Query("select p from Position p where p.id = :id")
  Optional<Position> findByIdLock(@Param("id") Long id);
}

