package app.restservice.apprestservice.Entities;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Test {

    @Id
    @GeneratedValue(generator = "test_generator")
    @SequenceGenerator(name = "test_generator", sequenceName = "test_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @NotEmpty
    private String name;

    @Getter
    @Setter
    @Min(0)
    Long categoryId;
}
