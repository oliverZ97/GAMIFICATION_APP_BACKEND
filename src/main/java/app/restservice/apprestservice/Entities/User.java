package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;


@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", initialValue = 1)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Email
    private String email;

    @Getter
    @Setter
    private String prename;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private String usercode;

    @Getter
    @Setter
    private Long experience_ID;

    @Getter
    @Setter
    private String password;
}


