package app.restservice.apprestservice.Entities;

import lombok.*;
import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserHash {
    @Id
    @GeneratedValue(generator = "userhash_generator")
    @SequenceGenerator(name = "userhash_generator", sequenceName = "userhash_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long user_ID;

    // 1 = new, 2 = edited, 3 = finished
    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private int hash;

    @Getter
    @Setter
    private String date_created;
}
