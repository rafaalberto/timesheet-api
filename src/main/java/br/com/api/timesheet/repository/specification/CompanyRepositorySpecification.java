package br.com.api.timesheet.repository.specification;

import br.com.api.timesheet.entity.Company;
import br.com.api.timesheet.resource.company.CompanyRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Optional;

public abstract class CompanyRepositorySpecification {

  private CompanyRepositorySpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static Specification<Company> criteriaSpecification(CompanyRequest request) {
    return (root, query, criteriaBuilder) -> {
      final HashSet<Predicate> predicates = new HashSet<>();
      setDocument(request, root, criteriaBuilder, predicates);
      setName(request, root, criteriaBuilder, predicates);
      return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    };
  }

  private static void setDocument(CompanyRequest request, Root<Company> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
    Optional<String> document = request.getDocument();
    if (document.isPresent()) {
      predicates.add(criteriaBuilder.like(
              (criteriaBuilder.lower(root.get("document"))), "%" + document.get() + "%")
      );
    }
  }

  private static void setName(CompanyRequest request, Root<Company> root, CriteriaBuilder criteriaBuilder, HashSet<Predicate> predicates) {
    Optional<String> name = request.getName();
    if (name.isPresent()) {
      predicates.add(criteriaBuilder.like(
              (criteriaBuilder.lower(root.get("name"))), "%" + name.get() + "%")
      );
    }
  }
}
