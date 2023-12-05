package MovieWebsite.service;

import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase  //we use database and beans gere, configure the test database to prevent changes to the real production database
public class UserAuthServiceIT {

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void testLoginSuccess() {

        UserAccount testUser = generateUserAccount();
        userService.registerUser(testUser);

        // login
        userAuthService.loginUser(testUser.getNickname(), testUser.getPassword());

        // Retrieve the user from the repository and verify the state
        UserAccount loggedInUser = userRepository.findByNickname(testUser.getNickname());
        assertNotNull(loggedInUser);
        assertTrue(loggedInUser.isLoggedIn());
        assertNotNull(loggedInUser.getAuthToken());
    }

    @Test
    @Transactional
    void testLoginFailPassword() {
        UserAccount testUser = generateUserAccount();
        userService.registerUser(testUser);

        assertThrows(IllegalArgumentException.class, () ->
                userAuthService.loginUser(testUser.getNickname(), "incorrectPassword"));

        // Ensure that no user is logged in and no authentication token is generated
        UserAccount user = userRepository.findByNickname(testUser.getNickname());
        assertFalse(Objects.equals(user.getPassword(), "incorrectPassword"));
    }

    @Test
    @Transactional
    void testLogoutSuccess() {
        UserAccount testUser = generateUserAccount();

        System.out.println(testUser.getId());
        userService.registerUser(testUser);

        //login
        userAuthService.loginUser(testUser.getNickname(), testUser.getPassword());

        //logout
//        userAuthService.logoutUser(testUser.getId());

        Optional<UserAccount> logoutUserOptional = userRepository.findById(testUser.getId());
        UserAccount logoutUser = logoutUserOptional.get();
        assertNotNull(logoutUser);
        assertFalse(logoutUser.isLoggedIn());
        assertNull(logoutUser.getAuthToken());
    }

    @Test
    @Transactional
    void testLogoutNonexistentUser() {
        UserAccount userAccount = null;
        // Attempt to logout a nonexistent user
//        assertThrows(NullPointerException.class, () ->
//                userAuthService.logoutUser(userAccount.getId()));
    }

    @Test
    @Transactional
    void testGenerateAndInvalidateAuthToken() {
        UserAccount testUser = generateUserAccount();
        userService.registerUser(testUser);

        System.out.println("Before generating auth token: " + userRepository.findByNickname(testUser.getNickname()));

        String authToken = userAuthService.generateAuthToken(testUser);

        System.out.println("Generated auth Token: " + authToken);

        // Verify that the user has the generated auth token
        UserAccount userWithAuthToken = userRepository.findByNickname(testUser.getNickname());
        assertNotNull(userWithAuthToken);
        assertEquals(authToken, userWithAuthToken.getAuthToken());

        // Invalidate the auth token
        userAuthService.invalidateAuthToken(authToken);

        System.out.println("After invalidating auth token: " + userRepository.findByNickname(testUser.getNickname()));

        // Verify that the user's auth token is null after invalidation
        UserAccount userAfterInvalidation = userRepository.findByNickname(testUser.getNickname());
        assertNotNull(userAfterInvalidation);
        assertNull(userAfterInvalidation.getAuthToken());
    }

    private UserAccount generateUserAccount() {
        UserService.UserRegistrationData data = UserService.UserRegistrationData.builder()
                .fullName("Eliza Johnson")
                .email("eliza@gmail.com")
                .nickname("testUser")
                .password("testPassword")
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
