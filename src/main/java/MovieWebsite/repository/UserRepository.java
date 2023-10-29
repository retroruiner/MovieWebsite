package MovieWebsite.repository;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;

public interface UserRepository {
    UserAccount findByFullName(String fullName); // Find a user by username
    UserAccount findByNickname(String nickname);
    UserAccount findByEmail(String email);
    UserAccount findByUserID(int id);
    void createUserAccount(UserAccount userAccount); //Creates a new user account.
    void updateUserAccount(UserAccount userAccount);// Updates an existing user account.
    void deleteUserAccount(int userId);
}
