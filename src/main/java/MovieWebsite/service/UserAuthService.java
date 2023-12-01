package MovieWebsite.service;

import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserAuthService {
    private final UserRepository userRepository;


    public UserAccount authenticateUser(String nickname, String password) {
        UserAccount userAccount = userRepository.findByNickname(nickname);
        UserAccount authenticatedUser = null;
        if (userAccount != null && password == userAccount.getPassword()) {
            return userAccount;
        }
        return null;
    }

    @Transactional
    public String generateAuthToken(UserAccount userAccount) {
        String authToken = generateRandomToken();
        userAccount.setAuthToken(authToken);
        userRepository.save(userAccount);
        return authToken;
    }

    public boolean verifyAuthToken(String authToken) {
        UserAccount userAccount = userRepository.findByAuthToken(authToken);
        return userAccount != null;
    }

    @Transactional
    public void invalidateAuthToken(String authToken) {
        UserAccount userAccount = userRepository.findByAuthToken(authToken);
        if (userAccount != null) {
            userAccount.setAuthToken(null);
        }
    }

    public String generateRandomToken() {
        byte[] tokenBytes = new byte[32];
        new SecureRandom().nextBytes(tokenBytes);
        return Base64.getEncoder().encodeToString(tokenBytes);

    }

    @Transactional
    public void loginUser(String nickname, String password) {
        UserAccount userAccount = authenticateUser(nickname, password);
        if (userAccount != null && verifyPassword(password, userAccount)) {
            String authToken = generateAuthToken(userAccount);
            userAccount.setAuthToken(authToken);
            userAccount.setLoggedIn(true);

            System.out.println("User " + nickname + " logged in");
        } else {
            throw new IllegalArgumentException("Such user does not exist");
        }
    }

    @Transactional
    public void logoutUser(int userId) {
        Optional<UserAccount> userOptional = userRepository.findById(userId);
        UserAccount user = userOptional.get();
        if (user != null) {
            String authToken = user.getAuthToken();
            invalidateAuthToken(authToken);
            user.setLoggedIn(false);
            user.setAuthToken(null);

            System.out.println("User logged out");
        } else {
            throw new NullPointerException("Such user does not exist");
        }
    }

    public boolean verifyPassword(String password, UserAccount user) {
        return (password == user.getPassword());
    }
}
