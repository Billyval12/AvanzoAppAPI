package org.example.avanzoapi.services.implementations;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.avanzoapi.domain.dtos.ChangePassDTO;
import org.example.avanzoapi.domain.dtos.RankingDTO;
import org.example.avanzoapi.domain.dtos.auth.UserRegisterDTO;
import org.example.avanzoapi.domain.dtos.auth.WhoamiDTO;
import org.example.avanzoapi.domain.entitites.Token;
import org.example.avanzoapi.domain.entitites.User;
import org.example.avanzoapi.repositories.TokenRepository;
import org.example.avanzoapi.repositories.UserRepository;
import org.example.avanzoapi.security.jwt.JWTTools;
import org.example.avanzoapi.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final JWTTools jwtTools;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserServiceImpl(JWTTools jwtTools, PasswordEncoder passwordEncoder, UserRepository userRepository, TokenRepository tokenRepository) {
        this.jwtTools = jwtTools;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User findUserByIdentifier(String identifier) {
        Integer userId = Integer.valueOf(identifier);

        return userRepository.findUserByUserIdAndActiveUserIsTrue(userId)
                .orElse(null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailAndActiveUserIsTrue(email)
                .orElse(null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void registerUser(UserRegisterDTO info) {
        User user = new User();

        user.setFirstName(info.getFirstName());
        user.setLastName(info.getLastName());
        user.setEmail(info.getEmail());
        user.setPassword(passwordEncoder.encode(info.getPassword()));
        user.setActiveUser(true);
        user.setPoints(0.0f);

        userRepository.save(user);
    }

    @Override
    public boolean checkPassword(User user, String password) {

        return !passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public WhoamiDTO whoami(User user) {
        WhoamiDTO info = new WhoamiDTO();
        info.setFirstName(user.getFirstName());
        info.setLastName(user.getLastName());
        info.setEmail(user.getEmail());
        info.setPoints(user.getPoints());

        return info;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanTokens(User user) {
        List<Token> tokens = tokenRepository.findAllByUserTokenAndIsTokenActive(user, true).orElse(null);

        assert tokens != null;
        tokens.forEach(token -> {
            if (!jwtTools.verifyToken(token.getContent())) {
                token.setIsTokenActive(false);
                tokenRepository.save(token);
            }
        });
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token registerToken(User user) {
        cleanPreviousTokens(user);

        new Token();
        Token token = Token.builder()
                .content(jwtTools.generateToken(user))
                .userToken(user)
                .isTokenActive(true)
                .build();

        tokenRepository.save(token);

        return token;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean validateToken(User user, String token) {
        cleanTokens(user);
        List<Token> tokens = tokenRepository.findAllByUserTokenAndIsTokenActive(user, true).orElse(null);

        tokens.stream()
                .filter(tk -> tk.getContent().equals(token))
                .findAny();

        return tokens.stream().anyMatch(tk -> tk.getContent().equals(token)); // Si un token cumple con la condicion dada devolvera true, sino devolver false
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanPreviousTokens(User user) {
        List<Token> tokens = tokenRepository.findAllByUserTokenAndIsTokenActive(user, true).orElse(null);

        if (tokens == null) {
            return;
        }

        tokens.forEach(token -> {
            token.setIsTokenActive(false);
            tokenRepository.save(token);
        });
    }

    @Override
    public User findUserAuthenticated() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findUserByEmailAndActiveUserIsTrue(email)
                .orElse(null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateUserPoints(Float score, User user) {
        Float actualScore = user.getPoints();
        Float gainedPoints = score * 15.0f;

        user.setPoints(actualScore + gainedPoints);
        userRepository.save(user);
    }

    @Override
    public List<RankingDTO> globalRanking() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    RankingDTO rankingDTO = new RankingDTO();
                    rankingDTO.setId(user.getUserId());
                    rankingDTO.setName(user.getFirstName() + " " + user.getLastName());
                    rankingDTO.setPoints(user.getPoints());

                    return rankingDTO;
                }).toList();
    }

    @Override
    public Boolean changePass(User user, ChangePassDTO info) {
        if (info.getNewPassword().equals(info.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(info.getNewPassword()));

            user.setPassword(passwordEncoder.encode(info.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
