package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserContent {

    @Id
    @GeneratedValue(generator = "usercontent_generator")
    @SequenceGenerator(name = "usercontent_generator", sequenceName = "usercontent_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long user_ID;

    @Getter
    @Setter
    private long content_ID;

    @Getter
    @Setter
    private int favourite;

    @Getter
    @Setter
    private boolean getReward;
}
