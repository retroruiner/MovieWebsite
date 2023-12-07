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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserAccount registerUser(UserAccount userAccount) {
        if (userRepository.findByEmail(userAccount.getEmail()) != null
                || userRepository.findByNickname(userAccount.getNickname()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with email " + userAccount.getEmail()
                    + " or nickname " + userAccount.getNickname() + " already exists.");
        }
        return userRepository.save(userAccount);
    }

    public void sendFriendRequest(UserAccount sender, UserAccount receiver){

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
