package app.restservice.apprestservice.Entities;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserAchievement {

    @Id
    @GeneratedValue(generator = "userachievement_generator")
    @SequenceGenerator(name = "userachievement_generator", sequenceName = "userachievement_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long user_ID;

    @Getter
    @Setter
    private long achievement_ID;

    @Getter
    @Setter
    private int count_achieved_user;
}
