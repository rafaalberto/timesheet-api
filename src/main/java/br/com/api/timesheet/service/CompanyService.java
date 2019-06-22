package br.com.api.timesheet.service;

import br.com.api.timesheet.entity.Company;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.CompanyCustomizedQueries;
import br.com.api.timesheet.repository.CompanyRepository;
import br.com.api.timesheet.resource.company.CompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    private CompanyCustomizedQueries companyCustomizedQueries;

    public CompanyService(@Autowired CompanyRepository companyRepository, @Autowired CompanyCustomizedQueries companyCustomizedQueries) {
        this.companyRepository = companyRepository;
        this.companyCustomizedQueries = companyCustomizedQueries;
    }

    public Page<Company> findAll(CompanyRequest companyRequest) {
        return companyCustomizedQueries.findAll(companyRequest);
    }

    public Company findById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new BusinessException("error-company-9", HttpStatus.BAD_REQUEST));
    }

    public Company save(Company company) {
        verifyIfCompanyExist(company);
        return companyRepository.save(company);
    }

    public void delete(Long id) {
        companyRepository.delete(findById(id));
    }

    private void verifyIfCompanyExist(final Company company) {
        Optional<Company> companyDB = companyRepository.findByDocument(company.getDocument());
        if (companyDB.isPresent() && companyDB.get().getId() != company.getId()) {
            throw new BusinessException("error-company-8", HttpStatus.BAD_REQUEST);
        }
    }
}
