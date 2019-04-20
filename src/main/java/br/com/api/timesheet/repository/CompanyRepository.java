package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Transactional(readOnly = true)
    Optional<Company> findByDocument(String document);
}

