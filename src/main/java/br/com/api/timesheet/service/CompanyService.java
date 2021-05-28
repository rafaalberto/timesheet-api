package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Company;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.CompanyRepository;
import br.com.api.timesheet.repository.CompanyRepositorySpecification;
import br.com.api.timesheet.resource.company.CompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.api.timesheet.utils.Constants.DEFAULT_PAGE;
import static br.com.api.timesheet.utils.Constants.DEFAULT_SIZE;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(@Autowired CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Page<Company> findAll(CompanyRequest request) {
        final Pageable pageable = PageRequest.of(request.getPage().orElse(DEFAULT_PAGE), request.getSize().orElse(DEFAULT_SIZE));
        return companyRepository.findAll(CompanyRepositorySpecification.criteriaSpecification(request), pageable);
    }

    public Company findById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new BusinessException("error-company-9", HttpStatus.BAD_REQUEST));
    }

    public Company save(CompanyRequest companyRequest) {
        Company company = Company.builder()
                .id(companyRequest.getId())
                .name(companyRequest.getName().orElse(""))
                .document(companyRequest.getDocument().orElse(null))
                .build();
        verifyIfCompanyExist(company);
        return companyRepository.save(company);
    }

    public void delete(Long id) {
        companyRepository.delete(findById(id));
    }

    private void verifyIfCompanyExist(final Company company) {
        Optional<Company> companyDB = companyRepository.findByDocument(company.getDocument());
        if (companyDB.isPresent() && !companyDB.get().getId().equals(company.getId())) {
            throw new BusinessException("error-company-8", HttpStatus.BAD_REQUEST);
        }
    }
}
