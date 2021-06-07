package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.User;
import br.com.api.timesheet.enumeration.ProfileEnum;
import br.com.api.timesheet.resource.user.UserRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Optional;

public abstract class UserRepositorySpecification {

    private UserRepositorySpecification() {
        throw new IllegalStateException("Utility class");
    }

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
        Optional<String> name = request.getName();
        if (name.isPresent()) {
            predicates.add(criteriaBuilder.like(
                    (criteriaBuilder.lower(root.get("name"))), "%" + name.get().toLowerCase() + "%")
            );
        }
    }

    private static void setUsername(UserRequest request, Root<User> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        Optional<String> username = request.getUsername();
        if (username.isPresent()) {
            predicates.add(criteriaBuilder.like(
                    (criteriaBuilder.lower(root.get("username"))), "%" + username.get().toLowerCase() + "%")
            );
        }
    }

    private static void setProfile(UserRequest request, Root<User> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        Optional<ProfileEnum> profile = request.getProfile();
        if (profile.isPresent()) {
            predicates.add(criteriaBuilder.equal(root.get("profile"), profile.get()));
        }
    }
}
