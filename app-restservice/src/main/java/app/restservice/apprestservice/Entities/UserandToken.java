package app.restservice.apprestservice.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
public class UserandToken {

    @NotEmpty
    @NotNull
    @Getter
    @Setter
    private User user;

    @NotEmpty
    @NotNull
    @Getter
    @Setter
    private String token;

}
