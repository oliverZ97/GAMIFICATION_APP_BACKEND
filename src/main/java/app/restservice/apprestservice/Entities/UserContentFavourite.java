package app.restservice.apprestservice.Entities;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserContentFavourite {

    @Getter
    @Setter
    private long user_content_ID;

    @Getter
    @Setter
    private long user_ID;

    @Getter
    @Setter
    private long content_ID;

    @Getter
    @Setter
    private int favourite;
}
