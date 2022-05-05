package app.restservice.apprestservice.Entities;

import javax.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserCategory {

    @Id
    @GeneratedValue(generator = "usercategory_generator")
    @SequenceGenerator(name = "usercategory_generator", sequenceName = "usercategory_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long user_ID;

    @Getter
    @Setter
    private long category_ID;

    @Getter
    @Setter
    private int favourite;
}
