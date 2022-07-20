package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private String title;

    @Getter
    @Setter
    @NotNull
    private String key;

    @Getter
    @Setter
    @NotNull
    private int key_count;

    @Getter
    @Setter
    private String group;

    @Getter
    @Setter
    @NonNull
    private String description;

    @Getter
    @Setter
    @NotNull
    private int experience;

    @Getter
    @Setter
    @Min(1)
    @Max(4)
    private int rarity;

}
