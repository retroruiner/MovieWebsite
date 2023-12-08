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

    private String generateAuthToken() {
        byte[] tokenBytes = new byte[32];
        new SecureRandom().nextBytes(tokenBytes);
        return Base64.getEncoder().encodeToString(tokenBytes);
    }

    private boolean verifyAuthToken(String authToken) {
        UserAccount userAccount = userRepository.findByAuthToken(authToken);
        return userAccount != null;
    }

    private UserAccount authenticateUser(String nickname, String password) {
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
            String authToken = generateAuthToken();
            updateUserStatus(userAccount, authToken, true);
            return authToken;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect nickname or password");
        }
    }

    private void updateUserStatus(UserAccount userAccount, String authToken, boolean status) {
        userAccount.setAuthToken(authToken);
        userAccount.setLoggedIn(status);
        userRepository.save(userAccount);
    }

    @Transactional
    public boolean logoutUser(String authToken) {
        UserAccount user = userRepository.findByAuthToken(authToken);
        if (user != null) {
            updateUserStatus(user, null, false);
            return true;
        }
        return false;
    }

    private UserAccount fetchUser(int userID) {
        Optional<UserAccount> userOptional = userRepository.findById(userID);
        return userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
    }
}
