package app.restservice.apprestservice.Entities;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Topic {

    @Id
    @GeneratedValue(generator = "topic_generator")
    @SequenceGenerator(name = "topic_generator", sequenceName = "topic_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @NotEmpty
    private String name;

    @Getter
    @Setter
    Long category_ID;
}
