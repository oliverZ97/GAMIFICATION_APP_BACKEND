package app.restservice.apprestservice.Entities;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class UserAndToken {

    @Getter
    @Setter
    @NotEmpty
    @NotNull
    public User user;

    @Getter
    @Setter
    @NotEmpty
    @NotNull
    public String token;
}
