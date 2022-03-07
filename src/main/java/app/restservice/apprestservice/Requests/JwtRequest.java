package app.restservice.apprestservice.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//This class is required for storing the username and password we recieve from the client.
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @Getter
    @Setter
    @NotNull
    @NotNull
    private String username;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String password;

}

