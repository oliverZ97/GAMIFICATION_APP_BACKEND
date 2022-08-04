package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserLog {

    @Id
    @GeneratedValue(generator = "userlog_generator")
    @SequenceGenerator(name = "userlog_generator", sequenceName = "userlog_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long user_ID;

    // 1 = level, 2 = quest, 3 = achievement, 4 = streak
    @Getter
    @Setter
    private int type;

    // 1 = new, 2 = edited, 3 = finished
    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private String info;

    @Getter
    @Setter
    private String date_created;
}
