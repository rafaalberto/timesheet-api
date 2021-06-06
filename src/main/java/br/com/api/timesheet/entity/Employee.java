package br.com.api.timesheet.entity;

import br.com.api.timesheet.enumeration.OfficeHoursEnum;
import br.com.api.timesheet.enumeration.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Locale;

import static java.text.NumberFormat.getCurrencyInstance;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = Employee.TABLE_NAME)
public class Employee implements Serializable {

    static final String TABLE_NAME = "employees";
    private static final String SEQUENCE_NAME = "seq_employees";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @NotBlank(message = "error-employee-1")
    @Size(min = 5, max = 50, message = "error-employee-2")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotBlank(message = "error-employee-3")
    @Column(name = "record_number", nullable = false, length = 10)
    private String recordNumber;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "cost_center", length = 20)
    private String costCenter;

    @Column(name = "cost_hour", precision = 10, scale = 2)
    private Double costHour;

    @Column(name = "status", nullable = false, length = 1)
    private StatusEnum status;

    @Column(name = "office_hour", nullable = false, length = 1)
    private OfficeHoursEnum officeHour;

    @Transient
    private String officeHourDescription;

    public String getCostHourFormatted() {
        return getCurrencyInstance(new Locale("pt", "BR")).format(costHour);
    }
}
