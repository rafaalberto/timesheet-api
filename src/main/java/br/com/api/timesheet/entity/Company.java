package br.com.api.timesheet.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = Company.TABLE_NAME)
public class Company implements Serializable {

  static final String TABLE_NAME = "companies";
  private static final long serialVersionUID = 52435565579515988L;
  private static final String SEQUENCE_NAME = "seq_companies";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
  private Long id;

  @Column(name = "document", unique = true, nullable = false, length = 18)
  private String document;

  @Column(name = "name", nullable = false, length = 50)
  private String name;
}
