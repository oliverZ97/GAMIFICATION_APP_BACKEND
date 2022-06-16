package app.restservice.apprestservice.Entities;

import lombok.*;
import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Level {
    @Id
    @GeneratedValue(generator = "level_generator")
    @SequenceGenerator(name = "level_generator", sequenceName = "level_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private int level;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int experience;
}
