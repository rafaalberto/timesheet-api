package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepository extends JpaRepository<Company, Long>,
        JpaSpecificationExecutor<Company> {

  @Transactional(readOnly = true)
  Optional<Company> findByDocument(String document);
}

