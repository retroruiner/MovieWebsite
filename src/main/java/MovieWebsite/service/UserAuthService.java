package MovieWebsite.service;

import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserAuthService {
    private final UserRepository userRepository;

    @Transactional
    public String generateAuthToken(UserAccount userAccount) {
        byte[] tokenBytes = new byte[32];
        new SecureRandom().nextBytes(tokenBytes);
        return Base64.getEncoder().encodeToString(tokenBytes);
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

    public UserAccount authenticateUser(String nickname, String password) {
        UserAccount userAccount = userRepository.findByNickname(nickname);

        System.out.println(Objects.equals(userAccount.getPassword(), password));
        if (Objects.equals(userAccount.getPassword(), password)) {

            return userAccount;
        }
        return null;
    }

    @Transactional
    public String loginUser(String nickname, String password) {
        UserAccount userAccount = authenticateUser(nickname, password);
        if (userAccount != null) {
            String authToken = generateAuthToken(userAccount);
            updateUserToken(userAccount, authToken);
            return authToken;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect nickname or password");
        }
    }

    private void updateUserToken(UserAccount userAccount, String authToken) {
        userAccount.setAuthToken(authToken);
        userAccount.setLoggedIn(true);
        userRepository.save(userAccount);
    }

    @Transactional
    public void logoutUser(int userId) {
        UserAccount user = fetchUser(userId);
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
    private UserAccount fetchUser(int userID) {
        Optional<UserAccount> userOptional = userRepository.findById(userID);
        return userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
