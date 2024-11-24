package org.example.avanzoapi.repositories;

import org.example.avanzoapi.domain.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserIdAndActiveUserIsTrue(Integer userId);
    Optional<User> findUserByEmailAndActiveUserIsTrue(String email);
}
