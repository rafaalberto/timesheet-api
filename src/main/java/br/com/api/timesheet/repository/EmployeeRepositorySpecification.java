package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.resource.employee.EmployeeRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.HashSet;

public class EmployeeRepositorySpecification {

    public static Specification<Employee> criteriaSpecification(EmployeeRequest request) {
        return (root, query, criteriaBuilder) -> {
            final HashSet<Predicate> predicates = new HashSet<>();
            setName(request, root, criteriaBuilder, predicates);
            setRecordNumber(request, root, criteriaBuilder, predicates);
            setStatus(request, root, criteriaBuilder, predicates);
            setCompany(request, root, criteriaBuilder, predicates);
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

    private static void setName(EmployeeRequest request, Root<Employee> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        if (request.getName().isPresent()) {
            predicates.add(criteriaBuilder.like(
                    (criteriaBuilder.lower(root.get("name"))), "%" + request.getName().get().toLowerCase() + "%")
            );
        }
    }

    private static void setRecordNumber(EmployeeRequest request, Root<Employee> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        if (request.getRecordNumber().isPresent()) {
            predicates.add(criteriaBuilder.like(
                    (criteriaBuilder.lower(root.get("recordNumber"))), "%" + request.getRecordNumber().get().toLowerCase() + "%")
            );
        }
    }

    private static void setStatus(EmployeeRequest request, Root<Employee> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        if (request.getStatus().isPresent()) {
            predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus().get()));
        }
    }

    private static void setCompany(EmployeeRequest request, Root<Employee> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
        final Join companyJoin = root.join("company", JoinType.LEFT);
        if (request.getCompanyId().isPresent()) {
            predicates.add(criteriaBuilder.equal(companyJoin.get("id"), request.getCompanyId().get()));
        }
    }
}
