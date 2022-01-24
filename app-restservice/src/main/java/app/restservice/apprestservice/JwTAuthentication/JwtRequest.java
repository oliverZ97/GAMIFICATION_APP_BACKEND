package app.restservice.apprestservice.JwTAuthentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {

    private static final Long serialVersionUID = 5926468583005150707L;

    @Getter
    @Setter
    @NotBlank
    @NotNull
    private String userName;

    @Getter
    @Setter
    @NotBlank
    @NotNull
    private String passwordHash;

}
