package app.restservice.apprestservice.Entities;

import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthenticationResponse {

    @Getter
    @Setter
    @Email
    private String username;

    @Getter
    @Setter
    private int hash;

    @Getter
    @Setter
    private Long user_id;
}
