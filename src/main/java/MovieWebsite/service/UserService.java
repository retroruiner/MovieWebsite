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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MovieCollectionService movieCollectionService;

    @Transactional
    public void registerUser(UserAccount userAccount) {
        //validateUserDoesNotExist(userRegistrationData.email, userRegistrationData.nickname);
        validateUserDoesNotExist(userAccount.getEmail(), userAccount.getNickname());
        userRepository.save(userAccount);
    }

    private void validateUserDoesNotExist(String email, String nickname) {
        if (userRepository.findByEmail(email) != null || userRepository.findByNickname(nickname) != null) {
            throw new IllegalArgumentException("User with the given credentials already exists");
        }
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

    @Getter
    @Builder
    public static class UserRegistrationData {
        private String fullName;
        private String email;
        private String nickname;
        private String password;
        private Date dateOfBirth;
    }

}
