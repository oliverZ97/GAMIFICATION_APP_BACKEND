package app.restservice.apprestservice.Entities;

import lombok.*;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class UserLogSorted {

    @Getter
    @Setter
    private List<UserLog> levelLogs;

    @Getter
    @Setter
    private List<UserLog> questLogs;

    @Getter
    @Setter
    private List<UserLog> achievementLogs;

    @Getter
    @Setter
    private List<UserLog> streakLogs;
}
