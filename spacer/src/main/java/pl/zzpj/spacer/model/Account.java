package pl.zzpj.spacer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("accounts")
public class Account {

    @Id
    @Getter
    private Long id;

    @NotNull
    @Getter
    @Size(min = 1, max = 32)
    private String username;

    @NotNull
    @Getter
    @Setter
    @Size(min = 60, max = 64)
    private String password;

    @NotNull
    @Getter
    @Setter
    private String firstName;

    @NotNull
    @Getter
    @Setter
    private String lastName;

    @Builder.Default
    @Getter
    @Setter
    private List<String> likedPhotos = new ArrayList<>();
}