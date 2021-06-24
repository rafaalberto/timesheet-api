package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Company;
import br.com.api.timesheet.resource.company.CompanyRequest;
import org.springframework.data.domain.Page;

public interface CompanyService {
  Page<Company> findAll(CompanyRequest request);

  Company findById(Long id);

  Company save(CompanyRequest companyRequest);

  void delete(Long id);
}
