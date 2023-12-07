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

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserAccountDto userAccountDto) {
        String authToken = userAuthService.loginUser(userAccountDto.getNickname(), userAccountDto.getPassword());
        return ResponseEntity.ok(authToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody UserAccountDto userAccountDto) {
        boolean isSuccess = userAuthService.logoutUser(userAccountDto.getAuthToken());
        return isSuccess ? ResponseEntity.ok("Logged out successfully") : ResponseEntity.badRequest().build();
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @DeleteMapping("/deleteByToken")
    public void delete(@RequestBody UserAccountDto userAccountDto) {
        userRepository.deleteByAuthToken(userAccountDto.getAuthToken());
    }

    @PutMapping("/user/{id}")
    public UserAccountDto updateUser(@RequestBody UserAccountDto newUser, @PathVariable int id) {
        return userAccountMapper.userToDto(userRepository.findById(id)
                .map(user -> {
                    user.setFullName(newUser.getFullName());
                    user.setNickname(newUser.getNickname());
                    user.setEmail(newUser.getEmail());
                    user.setDateOfBirth(newUser.getDateOfBirth());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/findAll")
    public List<UserAccountDto> findAll() {
        System.out.println("LOLSFKSFISHFSHFHFSJHFSHFSHFSHHFHSFJSHJSFHSK");
        return userAccountMapper.usersToDtos(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserAccountDto findById(@PathVariable int id) {
        return userAccountMapper.userToDto(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}
