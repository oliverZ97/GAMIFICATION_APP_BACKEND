package app.restservice.apprestservice.Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {

    public User(String email, String prename, String surname) {
        this.email = email;
        this.prename = prename;
        this.surname = surname;
        this.date_created = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        this.usercode = "#" + surname.substring(0, 2) + prename.substring(0, 2);
    }

    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", initialValue = 1)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String email;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String prename;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String surname;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String usercode;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private Long experience_id;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String date_created;

    @Getter
    @Setter
    @NotBlank
    @NotNull
    private String passwordHash;

}
