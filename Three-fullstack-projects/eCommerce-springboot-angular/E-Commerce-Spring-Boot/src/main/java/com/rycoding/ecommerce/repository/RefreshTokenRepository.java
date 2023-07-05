package com.rycoding.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.rycoding.ecommerce.entities.RefreshToken;
import com.rycoding.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query(value ="""
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """, nativeQuery = true)
    List<RefreshToken> findAllValidTokenByUser(Long id);

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}
