package MovieWebsite;

import MovieWebsite.service.InitDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class MovieWebsite implements CommandLineRunner {
    private final InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(MovieWebsite.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDbService.initDb();
    }
}
