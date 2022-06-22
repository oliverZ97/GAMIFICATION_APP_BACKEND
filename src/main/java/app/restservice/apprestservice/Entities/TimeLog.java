package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class TimeLog {

    @Id
    @GeneratedValue(generator = "timelog_generator")
    @SequenceGenerator(name = "timelog_generator", sequenceName = "timelog_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    // 1 = daily, 2 = weekly, 3 = monthly
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

    @Getter
    @Setter
    private String date_end;
}
