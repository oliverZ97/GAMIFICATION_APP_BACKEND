package app.restservice.apprestservice.Entities;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserQuest {

    @Id
    @GeneratedValue(generator = "userquest_generator")
    @SequenceGenerator(name = "userquest_generator", sequenceName = "userquest_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long user_ID;

    @Getter
    @Setter
    private long quest_ID;

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
}
