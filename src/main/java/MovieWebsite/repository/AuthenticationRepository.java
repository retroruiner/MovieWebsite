package MovieWebsite.repository;

import MovieWebsite.model.UserAccount;

public interface AuthenticationRepository {
    UserAccount authenticateUser(String username, String password);
    String generateAuthToken(UserAccount userAccount);
    boolean verifyAuthToken(String authToken);
    void invalidateAuthToken(String authToken);
}
