package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class TopicContent {
    @Id
    @GeneratedValue(generator = "topiccontent_generator")
    @SequenceGenerator(name = "topiccontent_generator", sequenceName = "topiccontent_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long content_ID;

    @Getter
    @Setter
    private long topic_ID;
}
