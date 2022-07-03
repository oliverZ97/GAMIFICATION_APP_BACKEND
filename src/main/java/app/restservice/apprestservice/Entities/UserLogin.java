package app.restservice.apprestservice.Entities;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserLogin {
    @Getter
    @Setter
    private String username;
}
