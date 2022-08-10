package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Content {

    @Id
    @GeneratedValue(generator = "content_generator")
    @SequenceGenerator(name = "content_generator", sequenceName = "content_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @NotEmpty
    private String title;

    @Getter
    @Setter
    @NotEmpty
    private String content;

    @Getter
    @Setter
    @NotEmpty
    private String topic_IDs;

    @Getter
    @Setter
    private String author;

    @Getter
    @Setter
    private String source;

    // TODO: Berechnung durch das System anhand der Words
    @Getter
    @Setter
    private int experience;

    // TODO: Berechnung durch das System
    @Getter
    @Setter
    private int number_of_words;

}
