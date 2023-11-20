package MovieWebsite.repository;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer> {  //type of the entity, type of PK(int id)
    UserAccount findByFullName(String fullName); // Find a user by username
    UserAccount findByNickname(String nickname);
    UserAccount findByEmail(String email);
    void deleteById(int userId);
    UserAccount findByAuthToken(String token);
}
