package org.example.avanzoapi.repositories;

import org.example.avanzoapi.domain.entitites.Token;
import org.example.avanzoapi.domain.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<List<Token>> findAllByUserTokenAndIsTokenActive(User user, boolean isTokenActive);
}
