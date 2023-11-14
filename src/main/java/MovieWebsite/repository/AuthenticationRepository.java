package MovieWebsite.repository;

import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<UserAccount, Integer> { //TODO: check JpaRepository<UserAccount
    UserAccount authenticateUser(String username, String password);
    String generateAuthToken(UserAccount userAccount);
    boolean verifyAuthToken(String authToken);
    void invalidateAuthToken(String authToken);
}
