package MovieWebsite.repository;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Integer> {  //type of the entity, type of PK(int id)
    UserAccount findByFullName(String fullName); // Find a user by username
    UserAccount findByNickname(String nickname);
    UserAccount findByEmail(String email);

    //<S extends T> S save(S entity); responsible for creating and updating entity

    void deleteUserAccount(int userId);
}
