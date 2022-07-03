package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Quest {

    @Id
    @GeneratedValue(generator = "quest_generator")
    @SequenceGenerator(name = "quest_generator", sequenceName = "quest_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @NotEmpty
    private String title;

    @Getter
    @Setter
    @NotEmpty
    private String description;

    // 1 - Daily, 2 - Weekly, 3 - Monthly, 4 - Unique
    @Getter
    @Setter
    @Min(1)
    @Max(4)
    @NotEmpty
    private int type;

    @Getter
    @Setter
    private int experience;

    // Name der Zieltabelle. z.b. Topic
    @Getter
    @Setter
    private String key;

    // min_level um diese Quest zu erhalten
    @Getter
    @Setter
    private int min_level;

    // Anzahl der entsprechenden zu lesenden contents. z.B. 10 Contents zum topic
    // Politik
    @Getter
    @Setter
    private int key_count;
}
