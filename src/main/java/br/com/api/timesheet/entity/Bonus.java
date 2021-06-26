package br.com.api.timesheet.entity;

import static java.text.NumberFormat.getCurrencyInstance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = Bonus.TABLE_NAME)
public class Bonus implements Serializable {

  static final String TABLE_NAME = "bonuses";
  private static final long serialVersionUID = 4967859640922628146L;
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

  public String getCostFormatted() {
    return getCurrencyInstance(new Locale("pt", "BR")).format(cost);
  }

}

