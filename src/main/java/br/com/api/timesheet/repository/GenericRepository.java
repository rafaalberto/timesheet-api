package br.com.api.timesheet.repository;

import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Map;

public class GenericRepository {

    @PersistenceContext
    private EntityManager entityManager;

    protected StringBuilder buildOperator(StringBuilder builderQuery) {
        return builderQuery.append(!builderQuery.toString().contains("where") ? "where" : "and");
    }

    protected Query getQuery(Pageable pageable, Map<String, Object> params, StringBuilder builderQuery) {
        Query query = entityManager.createQuery(builderQuery.toString());
        params.entrySet().forEach(param -> query.setParameter(param.getKey(), param.getValue()));
        query.setFirstResult(pageable.getPageNumber());
        query.setMaxResults(pageable.getPageSize());
        return query;
    }
}
