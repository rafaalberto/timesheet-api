package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Company;
import br.com.api.timesheet.resource.company.CompanyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CompanyCustomizedQueries extends GenericRepository {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Transactional(readOnly = true)
    public Page<Company> findAll(CompanyRequest companyRequest) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder builderQuery = new StringBuilder();
        builderQuery.append("SELECT c from Company c ");

        if (companyRequest.getDocument().isPresent()) {
            buildOperator(builderQuery).append(" lower(c.document) like lower (concat('%', :document, '%'))");
            params.put("document", companyRequest.getDocument().get());
        }

        if (companyRequest.getName().isPresent()) {
            buildOperator(builderQuery).append(" lower(c.name) like lower (concat('%', :name, '%'))");
            params.put("name", companyRequest.getName().get());
        }

        Pageable pageable = PageRequest.of(companyRequest.getPage().orElse(DEFAULT_PAGE),
                companyRequest.getSize().orElse(DEFAULT_SIZE));

        return new PageImpl<>(getQuery(pageable, params, builderQuery).getResultList());
    }

}
