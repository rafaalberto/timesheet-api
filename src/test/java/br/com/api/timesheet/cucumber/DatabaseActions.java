package br.com.api.timesheet.cucumber;

import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class DatabaseActions {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PositionRepository positionRepository;

    public DatabaseActions(EntityManager entityManager, PositionRepository positionRepository) {
        this.entityManager = entityManager;
        this.positionRepository = positionRepository;
    }

    @Transactional
    public void clear() {
        positionRepository.deleteAll();
    }

    public void insertPosition(final Position position) {
        final String insert = "INSERT INTO POSITIONS (id, title, dangerousness) VALUES (:id, :title, :dangerousness)";
        Query query = entityManager.createNativeQuery(insert);
        query.setParameter("id", position.getId());
        query.setParameter("title", position.getTitle());
        query.setParameter("dangerousness", false);
        query.executeUpdate();
    }

}
