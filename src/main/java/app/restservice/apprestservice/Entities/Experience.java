package app.restservice.apprestservice.Entities;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Experience {

    @Id
    @GeneratedValue(generator = "experience_generator")
    @SequenceGenerator(name = "experience_generator", sequenceName = "experience_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long user_ID;

    @Getter
    @Setter
    private int experience_value;

    @Getter
    @Setter
    private int level;
}
