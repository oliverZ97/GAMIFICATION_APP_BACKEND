package app.restservice.apprestservice.Entities;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Achievement {

    @Id
    @GeneratedValue(generator = "achievement_generator")
    @SequenceGenerator(name = "achievement_generator", sequenceName = "achievement_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @NotEmpty
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private int experience;

    @Getter
    @Setter
    private int rarity;

}
