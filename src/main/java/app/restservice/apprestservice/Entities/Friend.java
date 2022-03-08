package app.restservice.apprestservice.Entities;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Friend {

    @Id
    @GeneratedValue(generator = "friend_generator")
    @SequenceGenerator(name = "friend_generator", sequenceName = "friend_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long user_request_ID;

    @Getter
    @Setter
    private long user_owner_ID;

    //0 - abgehlent, 1 - angefragt, 2 - angenommen
    @Getter
    @Setter
    @Min(0)
    @Max(2)
    private int request_status;
}
