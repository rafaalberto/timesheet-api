package br.com.api.timesheet.entity;

import br.com.api.timesheet.enumeration.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = User.TABLE_NAME)
public class User implements Serializable {

  static final String TABLE_NAME = "users";
  private static final long serialVersionUID = -4803349741695893029L;
  private static final String SEQUENCE_NAME = "seq_users";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
  private Long id;

  @Column(name = "username", unique = true, nullable = false, length = 20)
  private String username;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "profile", nullable = false, length = 2)
  private ProfileEnum profile;

  @JsonIgnore
  @JsonProperty(value = "password")
  public String getPassword() {
    return password;
  }
}
