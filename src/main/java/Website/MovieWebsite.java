package Website;

import MovieWebsite.model.UserAccount;
import MovieWebsite.repository.UserRepository;
import MovieWebsite.service.InitDbService;
import MovieWebsite.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@SpringBootApplication
public class MovieWebsite implements CommandLineRunner {
    private InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(MovieWebsite.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDbService.initDb();
    }
}
