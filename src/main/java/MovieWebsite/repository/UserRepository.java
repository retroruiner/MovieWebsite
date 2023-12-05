package MovieWebsite.repository;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer> {  //type of the entity, type of PK(int id)
    UserAccount findByFullName(String fullName); // Find a user by username
    UserAccount findByNickname(String nickname);
    UserAccount findByEmail(String email);
    void deleteById(int userId);
    void deleteByAuthToken(String authToken);
    UserAccount findByAuthToken(String token);

    @Modifying
    @Query("UPDATE UserAccount user SET user.authToken = ?2 WHERE user.id = ?1")
    void updateUserToken(int userId, String token);


}
