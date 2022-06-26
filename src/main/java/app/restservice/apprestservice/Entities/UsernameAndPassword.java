package app.restservice.apprestservice.Entities;

import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UsernameAndPassword {

    @Getter
    @Setter
    @Email
    private String username;

    @Getter
    @Setter
    private String password;
}
