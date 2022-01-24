package app.restservice.apprestservice.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Entity
@Table(name = "test")
public class TestEntity {

    public TestEntity(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(generator = "unit_generator")
    @SequenceGenerator(name = "unit_generator", sequenceName = "unit_sequence", initialValue = 1)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String title;

}
