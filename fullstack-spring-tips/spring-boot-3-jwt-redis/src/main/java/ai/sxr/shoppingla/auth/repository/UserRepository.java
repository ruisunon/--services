package ai.sxr.shoppingla.auth.repository;

import java.util.Optional;

import ai.sxr.shoppingla.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

}
