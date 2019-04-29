package br.com.api.timesheet.repository;

import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.resource.employee.EmployeeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeCustomizedQueries extends GenericRepository {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Transactional(readOnly = true)
    public Page<Employee> findAll(EmployeeRequest employeeRequest) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder builderQuery = new StringBuilder();
        builderQuery.append("SELECT e from Employee e ");

        if (employeeRequest.getName().isPresent()) {
            buildOperator(builderQuery).append(" lower(e.name) like lower (concat('%', :name, '%'))");
            params.put("name", employeeRequest.getName().get());
        }

        Pageable pageable = PageRequest.of(employeeRequest.getPage().orElse(DEFAULT_PAGE),
                employeeRequest.getSize().orElse(DEFAULT_SIZE));

        return new PageImpl<>(getQuery(pageable, params, builderQuery).getResultList());
    }

}
