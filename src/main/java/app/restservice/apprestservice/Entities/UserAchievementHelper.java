package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserAchievementHelper {

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
    private Achievement achievement;

    @Getter
    @Setter
    private int count_achieved_user;

    @Getter
    @Setter
    private int goal_value;

    @Getter
    @Setter
    private int progress_value;

    @Getter
    @Setter
    private String start_date;

    @Getter
    @Setter
    private String end_date;

    @Getter
    @Setter
    private int status;
}
