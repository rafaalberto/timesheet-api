package br.com.api.timesheet.service.impl;

import static br.com.api.timesheet.utils.Constants.DEFAULT_PAGE;
import static br.com.api.timesheet.utils.Constants.DEFAULT_SIZE;

import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.PositionRepository;
import br.com.api.timesheet.repository.specification.PositionRepositorySpecification;
import br.com.api.timesheet.resource.position.PositionRequest;
import br.com.api.timesheet.service.PositionService;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

  @Autowired
  private PositionRepository positionRepository;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Find all positions.
   *
   * @param request - request
   * @return
   */
  public Page<Position> findAll(PositionRequest request) {
    final Pageable pageable = PageRequest.of(
            request.getPage().orElse(DEFAULT_PAGE), request.getSize().orElse(DEFAULT_SIZE));
    return positionRepository.findAll(
            PositionRepositorySpecification.criteriaSpecification(request), pageable);
  }

  /**
   * Find position by id.
   *
   * @param id - id
   * @return
   */
  public Position findById(Long id) {
    return positionRepository.findById(id)
            .orElseThrow(() -> new BusinessException("error-position-9", HttpStatus.BAD_REQUEST));
  }

  /**
   * Save position.
   *
   * @param positionRequest - request
   * @return
   */
  public Position save(PositionRequest positionRequest) {
    String title = positionRequest.getTitle().orElse("");
    Position position = Position.builder()
            .id(positionRequest.getId())
            .title(title.toUpperCase())
            .dangerousness(positionRequest.isDangerousness())
            .build();
    verifyIfPositionExist(position);
    return positionRepository.save(position);
  }

  public void delete(Long id) {
    positionRepository.delete(findById(id));
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
  public Position updateTitle(PositionRequest positionRequest) {
    try {
      Optional<Position> positionBD = positionRepository.findByIdLock(positionRequest.getId());

      Position position = positionBD.get();

      entityManager.refresh(position);

      position.setTitle(positionRequest.getTitle().get());
      position = positionRepository.save(position);
      return position;
    } catch (PessimisticLockingFailureException e) {
      log.error("lock exception = {}", e.getMessage());
    }
    return null;
  }

  private void verifyIfPositionExist(final Position position) {
    if (Optional.ofNullable(position.getId()).isPresent()) {
      findById(position.getId());
    }
    Optional<Position> positionDB = positionRepository.findByTitle(position.getTitle());
    if (positionDB.isPresent() && !positionDB.get().getId().equals(position.getId())) {
      throw new BusinessException("error-position-8", HttpStatus.BAD_REQUEST);
    }
  }
}
