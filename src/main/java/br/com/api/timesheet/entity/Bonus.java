package br.com.api.timesheet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = Bonus.TABLE_NAME)
public class Bonus implements Serializable {

    static final String TABLE_NAME = "bonuses";
    private static final String SEQUENCE_NAME = "seq_bonuses";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "month_reference", length = 2)
    private Integer monthReference;

    @Column(name = "year_reference", length = 4)
    private Integer yearReference;

    @Column(name = "code", length = 5)
    private String code;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "cost", precision = 10, scale = 2)
    private Double cost;

}
