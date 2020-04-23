package br.com.api.timesheet.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = Position.TABLE_NAME)
public class Position implements Serializable {

    private static final long serialVersionUID = 6345585748835039999L;

    static final String TABLE_NAME = "positions";
    private static final String SEQUENCE_NAME = "seq_positions";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @NotBlank(message = "error-position-1")
    @Size(min = 3, max = 50, message = "error-position-2")
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "dangerousness", nullable = false, length = 1)
    private boolean dangerousness;

    public void setTitle(String title) {
        this.title = title.toUpperCase();
    }
}
