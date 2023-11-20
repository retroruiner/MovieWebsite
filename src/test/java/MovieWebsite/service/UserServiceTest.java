package MovieWebsite.service;

import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    Calendar calendar = Calendar.getInstance();

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void testRegisterUser_Success() {
        // Given
        calendar.set(2003, Calendar.NOVEMBER, 19);
        Date date = calendar.getTime();

        UserService.UserRegistrationData userRegistrationData = UserService.UserRegistrationData.builder()
                .fullName("Eliza Johnson")
                .email("eliza@gmail.com")
                .nickname("LizzyWizzy")
                .password("some_password")
                .dateOfBirth(java.sql.Date.valueOf("2020-01-09"))
                .build();


        UserAccount userAccount = UserAccount.builder()
                .fullName(userRegistrationData.getFullName())
                .nickname(userRegistrationData.getNickname())
                .password(userRegistrationData.getPassword())
                .email(userRegistrationData.getEmail())
                .dateOfBirth(userRegistrationData.getDateOfBirth())
                .build();



        //Set environment such that such user does not exist
        Mockito.when(userRepository.findByEmail(userRegistrationData.getEmail())).thenReturn(null);
        Mockito.when(userRepository.findByNickname(userRegistrationData.getNickname())).thenReturn(null);

        // When
        userService.registerUser(userAccount);

        System.out.println(userAccount.getDateOfBirth());

        // Then
        //verify(userRepository, times(1)).save(userAccount);
        verify(userRepository, times(1)).save(userAccount);

    }

    @Test
    public void testRegisterUser_Fail() {
        // Given
        //calendar.set(2003, Calendar.NOVEMBER, 19);
        //Date date = calendar.getTime();

        UserService.UserRegistrationData userRegistrationData = UserService.UserRegistrationData.builder()
                .fullName("Eliza Johnson")
                .email("eliza@gmail.com")
                .nickname("LizzyWizzy")
                .password("some_password")
                .dateOfBirth(java.sql.Date.valueOf("2020-01-09"))
                .build();

        UserAccount userAccount = UserAccount.builder()
                .fullName(userRegistrationData.getFullName())
                .nickname(userRegistrationData.getNickname())
                .password(userRegistrationData.getPassword())
                .email(userRegistrationData.getEmail())
                .dateOfBirth(userRegistrationData.getDateOfBirth())
                .build();


        //TODO: remove lenient()
        //Set environment such that such user already exists
        Mockito.lenient().when(userRepository.findByEmail(userRegistrationData.getEmail())).thenReturn(userAccount);
        Mockito.lenient().when(userRepository.findByNickname(userRegistrationData.getNickname())).thenReturn(userAccount);

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userAccount));

        verify(userRepository, never()).save(Mockito.any(UserAccount.class));
    }

    //private void validateUserDoesNotExist(String email, String nickname)

}
