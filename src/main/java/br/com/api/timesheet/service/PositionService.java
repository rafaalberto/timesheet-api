package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.PositionCustomizedQueries;
import br.com.api.timesheet.repository.PositionRepository;
import br.com.api.timesheet.resource.position.PositionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PositionService {

    private PositionRepository positionRepository;

    private PositionCustomizedQueries positionCustomizedQueries;

    public PositionService(@Autowired PositionRepository positionRepository, @Autowired PositionCustomizedQueries positionCustomizedQueries) {
        this.positionRepository = positionRepository;
        this.positionCustomizedQueries = positionCustomizedQueries;
    }

    public Page<Position> findAll(PositionRequest positionRequest) {
        return positionCustomizedQueries.findAll(positionRequest);
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
        //TODO(1) - Refactor
        Optional<Position> positionDB;
        if(position.getId() != null) {
            positionDB = positionRepository.findById(position.getId());
            if(!positionDB.isPresent()) {
                throw new BusinessException("error-position-9", HttpStatus.BAD_REQUEST);
            }
        }
        positionDB = positionRepository.findByTitle(position.getTitle());
        if (positionDB.isPresent() && !positionDB.get().getId().equals(position.getId())) {
            throw new BusinessException("error-position-8", HttpStatus.BAD_REQUEST);
        }
    }
}
