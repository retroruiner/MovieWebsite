package MovieWebsite.service;

import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.AuthenticationRepository;
import MovieWebsite.repository.UserRepository;

public class UserAuthService {
    private UserRepository userRepository;
    private AuthenticationRepository authenticationRepository;

    public void loginUser(String nickname, String password) {
        UserAccount userAccount = authenticationRepository.authenticateUser(nickname, password);

        if (userAccount != null && userAccount.verifyPassword(password)) {
            String authToken = authenticationRepository.generateAuthToken(userAccount);
            userAccount.setAuthToken(authToken);
            userAccount.setLoggedIn(true);

            System.out.println("User " + nickname + " logged in");
        } else {
            throw new IllegalArgumentException("Such user does not exist");
        }
    }
    public void logoutUser(int userId) {
        UserAccount user = userRepository.findByUserID(userId);
        if (user != null) {
            String authToken = user.getAuthToken();
            authenticationRepository.invalidateAuthToken(authToken);
            user.setLoggedIn(false);
            user.setAuthToken(null);

            System.out.println("User logged out");
        } else {
            throw new IllegalArgumentException("Such user does not exist");
        }
    }
}
