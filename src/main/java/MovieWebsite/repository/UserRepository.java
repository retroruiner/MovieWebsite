package MovieWebsite.repository;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;

public interface UserRepository {
    UserAccount findByUsername(String username); // Find a user by username
    UserAccount findByUserID();
    void createUserAccount(UserAccount userAccount); //Creates a new user account.
    void updateUserAccount(UserAccount userAccount);// Updates an existing user account.
    void deleteUserAccount(int userId);
}
