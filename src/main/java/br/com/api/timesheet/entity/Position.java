package br.com.api.timesheet.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = Position.TABLE_NAME)
public class Position implements Serializable {

  static final String TABLE_NAME = "positions";
  private static final long serialVersionUID = 6345585748835039999L;
  private static final String SEQUENCE_NAME = "seq_positions";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
  private Long id;

  @Column(name = "title", nullable = false, length = 50)
  private String title;

  @Column(name = "dangerousness", nullable = false, length = 1)
  private boolean dangerousness;

  public void setTitle(String title) {
    this.title = title.toUpperCase();
  }
}
