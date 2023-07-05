package io.prots.app.repository;

import io.prots.app.model.entity.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, String>  {
    Optional<User> findByUsername(String username);
    void save(User user);
}

