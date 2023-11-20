package MovieWebsite.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAccount {
    @Id
    @GeneratedValue
    private int id;

    private String fullName;
    private String nickname;
    private Date dateOfBirth;
    private String email;
    private String password;
    private boolean loggedIn;
    private String authToken; //TODO: change location of Token, not safe now

    //TODO: Implement hashing algorithm

    @Builder.Default
    @OneToMany(mappedBy = "userAccount")
    private List<MovieCollection> listOfCollections = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<UserAccount> friendList = new ArrayList<>();
}
