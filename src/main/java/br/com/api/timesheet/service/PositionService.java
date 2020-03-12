package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.PositionRepository;
import br.com.api.timesheet.repository.PositionRepositorySpecification;
import br.com.api.timesheet.resource.position.PositionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.api.timesheet.utils.Constants.DEFAULT_PAGE;
import static br.com.api.timesheet.utils.Constants.DEFAULT_SIZE;

@Service
public class PositionService {

    private PositionRepository positionRepository;

    public PositionService(@Autowired PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Page<Position> findAll(PositionRequest request) {
        final Pageable pageable = PageRequest.of(request.getPage().orElse(DEFAULT_PAGE), request.getSize().orElse(DEFAULT_SIZE));
        return positionRepository.findAll(PositionRepositorySpecification.criteriaSpecification(request), pageable);
    }

    public Position findById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("error-position-9", HttpStatus.BAD_REQUEST));
    }

    public Position save(Position position) {
        verifyIfPositionExist(position);
        return positionRepository.save(position);
    }

    public void delete(Long id) {
        positionRepository.delete(findById(id));
    }
    
    private void verifyIfPositionExist(final Position position) {
        Optional<Position> positionDB = positionRepository.findByTitle(position.getTitle());
        if (positionDB.isPresent() && !positionDB.get().getId().equals(position.getId())) {
            throw new BusinessException("error-position-8", HttpStatus.BAD_REQUEST);
        }
    }
}
