package MovieWebsite.web;

import MovieWebsite.UserAccountMapper;
import MovieWebsite.dto.UserAccountDto;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.UserRepository;
import MovieWebsite.service.UserAuthService;
import MovieWebsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserAuthService userAuthService;
    private final UserAccountMapper userAccountMapper;

    @PostMapping("/register")
    public UserAccountDto register(@RequestBody UserAccountDto user) {
        user.setId(0);
        return userAccountMapper.userToDto(
                userService.registerUser(userAccountMapper.dtoToUser(user))
        );
    }

//    @PostMapping("/login")
//    public String login(String nickname, String password) {
//        return userAuthService.loginUser(nickname, password);
//    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserAccountDto userAccountDto) {
        String authToken = userAuthService.loginUser(userAccountDto.getNickname(), userAccountDto.getPassword());
        return new ResponseEntity<>(authToken, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping
    public List<UserAccountDto> findAll() {
        return userAccountMapper.usersToDtos(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserAccountDto findById(@PathVariable int id) {
        return userAccountMapper.userToDto(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}
