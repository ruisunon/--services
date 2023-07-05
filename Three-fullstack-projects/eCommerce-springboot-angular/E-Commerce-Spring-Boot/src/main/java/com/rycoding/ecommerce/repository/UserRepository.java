package com.rycoding.ecommerce.repository;

import com.rycoding.ecommerce.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    // use JQL to update
    @Query("Update User t set t.img =: img where t.id =: id")
    @Modifying
    public void updateUserAvatar(Long id, byte[] img);

}