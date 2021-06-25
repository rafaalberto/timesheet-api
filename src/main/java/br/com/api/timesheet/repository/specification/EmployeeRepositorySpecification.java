package br.com.api.timesheet.repository.specification;

import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.enumeration.StatusEnum;
import br.com.api.timesheet.resource.employee.EmployeeRequest;
import java.util.HashSet;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public abstract class EmployeeRepositorySpecification {

  private EmployeeRepositorySpecification() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Criteria specification.
   * @param request - request
   * @return
   */
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

  private static void setName(EmployeeRequest request, Root<Employee> root,
      CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
    Optional<String> name = request.getName();
    if (name.isPresent()) {
      predicates.add(criteriaBuilder.like(
              (criteriaBuilder.lower(root.get("name"))), "%" + name.get().toLowerCase() + "%")
      );
    }
  }

  private static void setRecordNumber(EmployeeRequest request, Root<Employee> root,
      CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
    Optional<String> recordNumber = request.getRecordNumber();
    if (recordNumber.isPresent()) {
      predicates.add(criteriaBuilder.like(
              (criteriaBuilder.lower(root.get("recordNumber"))), "%"
                      + recordNumber.get().toLowerCase() + "%")
      );
    }
  }

  private static void setStatus(EmployeeRequest request, Root<Employee> root,
      CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
    Optional<StatusEnum> status = request.getStatus();
    if (status.isPresent()) {
      predicates.add(criteriaBuilder.equal(root.get("status"), status.get()));
    }
  }

  private static void setCompany(EmployeeRequest request, Root<Employee> root,
      CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
    final Join<Object, Object> companyJoin = root.join("company", JoinType.LEFT);
    Optional<Long> companyId = request.getCompanyId();
    if (companyId.isPresent()) {
      predicates.add(criteriaBuilder.equal(companyJoin.get("id"), companyId.get()));
    }
  }
}
