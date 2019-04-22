package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.resource.position.PositionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PositionCustomizedQueries extends GenericRepository {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Transactional(readOnly = true)
    public Page<Position> findAll(PositionRequest positionRequest) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder builderQuery = new StringBuilder();
        builderQuery.append("SELECT p from Position p ");

        if (positionRequest.getTitle().isPresent()) {
            buildOperator(builderQuery).append(" lower(p.title) like lower (concat('%', :title, '%'))");
            params.put("title", positionRequest.getTitle().get());
        }
        
        Pageable pageable = PageRequest.of(positionRequest.getPage().orElse(DEFAULT_PAGE),
                positionRequest.getSize().orElse(DEFAULT_SIZE));

        return new PageImpl<>(getQuery(pageable, params, builderQuery).getResultList());
    }

}
