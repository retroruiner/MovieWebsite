package MovieWebsite.service;

import MovieWebsite.model.MovieCollection;
import MovieWebsite.model.MovieItem;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.UserRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final MovieCollectionService movieCollectionService;

    public void registerUser(UserRegistrationData userRegistrationData) {
        validateUserDoesNotExist(userRegistrationData.email, userRegistrationData.nickname);
        UserAccount userAccount = createUserAccount(userRegistrationData);
        userRepository.save(userAccount);
    }

    private void validateUserDoesNotExist(String email, String nickname) {
        if (userRepository.findByEmail(email) != null || userRepository.findByNickname(nickname) != null) {
            throw new IllegalArgumentException("User with the given credentials already exists");
        }
    }
    private UserAccount createUserAccount(UserRegistrationData userRegistrationData) {
        return UserAccount.builder()
                .fullName(userRegistrationData.fullName)
                .nickname(userRegistrationData.nickname)
                .password(userRegistrationData.password)
                .email(userRegistrationData.email)
                .dateOfBirth(userRegistrationData.dateOfBirth)
                .build();
    }

    //TODO: delete generateRandomID()
    private int generateRandomId() {
        Random random = new Random();
        int id;
        do {
            id = 100000 + random.nextInt(999999);
        } while (!isUserIdAvailable(id));
        return id;
    }

    //TODO: delete isUserIdAvailable()
    private boolean isUserIdAvailable(int id) {
        Optional<UserAccount> existingUser = userRepository.findById(id);
        return existingUser == null;
    }

    public void setProfilePicture(UserAccount user, String imageUrl) {
        //TODO: set PFP
    }
    public void sendFriendRequest(UserAccount sender, UserAccount receiver){
        //TODO: Friend request
    }
    public void acceptFriendRequest(UserAccount user, UserAccount friend) {
        //TODO: Accept friend request
    }
    public void deleteFriend(UserAccount user, UserAccount friend) {
        //TODO: Delete friend
    }

    @Builder
    public static class UserRegistrationData {
        private String fullName;
        private String email;
        private String nickname;
        private String password;
        private Date dateOfBirth;
    }

}
