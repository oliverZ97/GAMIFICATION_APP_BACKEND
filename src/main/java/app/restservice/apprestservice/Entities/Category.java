package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(generator = "category_generator")
    @SequenceGenerator(name = "category_generator", sequenceName = "category_sequence", initialValue = 1)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @NotEmpty
    private String name;

    @Getter
    @Setter
    @NotNull
    private String minLevel;

}
