package MovieWebsite.repository;
import MovieWebsite.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer> {  //type of the entity, type of PK(int id)
    UserAccount findByFullName(String fullName); // Find a user by full name
    UserAccount findByNickname(String nickname);  // Find user by the nickname
    UserAccount findByEmail(String email); //Find user by email
    void deleteById(int userId); // Delete user with the specific ID
    void deleteByAuthToken(String authToken); // Delete user with the specific authentication token
    UserAccount findByAuthToken(String token); // Find user by his authentication token

    // Update user's token
    @Modifying
    @Query("UPDATE UserAccount user SET user.authToken = ?2 WHERE user.id = ?1")
    void updateUserToken(int userId, String token);


}
