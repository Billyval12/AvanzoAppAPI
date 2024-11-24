package org.example.avanzoapi.services;

import org.example.avanzoapi.domain.dtos.ChangePassDTO;
import org.example.avanzoapi.domain.dtos.RankingDTO;
import org.example.avanzoapi.domain.dtos.auth.UserRegisterDTO;
import org.example.avanzoapi.domain.dtos.auth.WhoamiDTO;
import org.example.avanzoapi.domain.entitites.User;
import org.example.avanzoapi.domain.entitites.Token;

import java.util.List;

public interface UserService {
    User findUserByIdentifier(String identifier);
    User findUserByEmail(String email);
    void registerUser(UserRegisterDTO info);
    boolean checkPassword(User user, String password);
    WhoamiDTO whoami(User user);

    // Token Management
    void cleanTokens(User user);
    Token registerToken(User user);
    Boolean validateToken(User user, String token);
    void cleanPreviousTokens(User user);
    User findUserAuthenticated();

    void updateUserPoints(Float score, User user);
    List<RankingDTO> globalRanking();

    Boolean changePass(User user, ChangePassDTO info);
}
