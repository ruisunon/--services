package ai.sxr.shoppingla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:8081")
@SpringBootApplication
//@EnableCaching
public class SxrShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SxrShoppingApplication.class, args);
    }
}
