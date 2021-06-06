package br.com.api.timesheet.unit.repository;

import br.com.api.timesheet.entity.Position;
import br.com.api.timesheet.resource.position.PositionRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Optional;

public abstract class PositionRepositorySpecification {

    private PositionRepositorySpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Position> criteriaSpecification(PositionRequest request) {
        return (root, query, criteriaBuilder) -> {
            final HashSet<Predicate> predicates = new HashSet<>();
            setTitle(request, root, criteriaBuilder, predicates);
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

    private static void setTitle(PositionRequest request, Root<Position> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        Optional<String> title = request.getTitle();
        if (title.isPresent()) {
            predicates.add(criteriaBuilder.like(
                    (criteriaBuilder.lower(root.get("title"))), "%" + title.get().toLowerCase() + "%")
            );
        }
    }
}
