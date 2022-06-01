package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserTopic {

    @Id
    @GeneratedValue(generator = "usertopic_generator")
    @SequenceGenerator(name = "usertopic_generator", sequenceName = "usertopic_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long user_ID;

    @Getter
    @Setter
    private long topic_ID;

    @Getter
    @Setter
    private int number_of_gains;

    @Getter
    @Setter
    private int favourite;
}
