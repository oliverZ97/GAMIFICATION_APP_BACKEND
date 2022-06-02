package app.restservice.apprestservice.Entities;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class DashboardEntry {

    @Getter
    @Setter
    @NotEmpty
    private String name;

    @Getter
    @Setter
    private List<Content> contents;

}
