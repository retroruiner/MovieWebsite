package MovieWebsite.web;

import MovieWebsite.dto.UserAccountDto;
import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.UserRepository;
import MovieWebsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public UserAccount create(@RequestBody UserAccount user) {
        return userRepository.save(user);
    }
//    @PostMapping
//    public UserAccountDto create(@RequestBody UserAccountDto user) {
//        return userRepository.save(user);
//    }

//    @GetMapping
//    public List<UserAccount> findAll() {
//        return userRepository.findAll();
//    }
}
