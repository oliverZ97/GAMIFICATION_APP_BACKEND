package app.restservice.apprestservice.Entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Streak {

    @Id
    @GeneratedValue(generator = "streak_generator")
    @SequenceGenerator(name = "streak_generator", sequenceName = "streak_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @NotEmpty
    private long user_id;

    @Getter
    @Setter
    private int day_count;

    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private String last_updated;

    @Getter
    @Setter
    private Boolean changed_today;

}
