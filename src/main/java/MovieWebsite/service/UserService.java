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
        validateUserDoesNotExist(userAccount.getEmail(), userAccount.getNickname());
        return userRepository.save(userAccount);
    }

    private void validateUserDoesNotExist(String email, String nickname) {
        if (userRepository.findByEmail(email) != null || userRepository.findByNickname(nickname) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with email " + email + " or nickname " + nickname + " already exists.");
        }
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
