package MovieWebsite.service;

import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase  //we use database and beans gere
public class UserAuthServiceIT {

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void testLoginLogout() {

        UserAccount userAccount = generateUserAccount();

        userService.registerUser(userAccount);

        //login
        userAuthService.loginUser(userAccount.getNickname(), userAccount.getPassword());

        assertNotNull(userAccount);
        assertTrue(userAccount.isLoggedIn());

        //logout
        userAuthService.logoutUser(userAccount.getId());

        assertNotNull(userAccount);
        assertFalse(userAccount.isLoggedIn());
        assertNull(userAccount.getAuthToken());
    }

    @Test
    void testGenerateAndVerifyAuthToken() {
        UserAccount userAccount = generateUserAccount();
        userService.registerUser(userAccount);

        userAuthService.loginUser(userAccount.getNickname(), userAccount.getPassword());

        String authToken = userAccount.getAuthToken();

        assertNotNull(userAccount);
        assertNotNull(authToken);
        assertTrue(userAuthService.verifyAuthToken(authToken));
    }

    private UserAccount generateUserAccount() {
        UserService.UserRegistrationData data = UserService.UserRegistrationData.builder()
                .fullName("Eliza Johnson")
                .email("eliza@gmail.com")
                .nickname("LizzyWizzy")
                .password("some_password")
                .dateOfBirth(java.sql.Date.valueOf("2020-01-09"))
                .build();

        return UserAccount.builder()
                .fullName(data.getFullName())
                .nickname(data.getNickname())
                .password(data.getPassword())
                .email(data.getEmail())
                .dateOfBirth(data.getDateOfBirth())
                .build();
    }


}
