package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.resource.user.UserRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;

public class UserRepositorySpecification {

    public static Specification<User> criteriaSpecification(UserRequest request) {
        return (root, query, criteriaBuilder) -> {
            final HashSet<Predicate> predicates = new HashSet<>();
            setName(request, root, criteriaBuilder, predicates);
            setUsername(request, root, criteriaBuilder, predicates);
            setProfile(request, root, criteriaBuilder, predicates);
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

    private static void setName(UserRequest request, Root<User> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        if (request.getName().isPresent()) {
            predicates.add(criteriaBuilder.like(
                    (criteriaBuilder.lower(root.get("name"))), "%" + request.getName().get().toLowerCase() + "%")
            );
        }
    }

    private static void setUsername(UserRequest request, Root<User> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        if (request.getUsername().isPresent()) {
            predicates.add(criteriaBuilder.like(
                    (criteriaBuilder.lower(root.get("username"))), "%" + request.getUsername().get().toLowerCase() + "%")
            );
        }
    }

    private static void setProfile(UserRequest request, Root<User> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        if (request.getProfile().isPresent()) {
            predicates.add(criteriaBuilder.equal(root.get("profile"), request.getProfile().get()));
        }
    }
}
