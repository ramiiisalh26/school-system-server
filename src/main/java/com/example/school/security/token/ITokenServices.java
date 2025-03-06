package com.example.school.security.token;

import java.util.List;
import java.util.Optional;

public interface ITokenServices {
    
    void saveToken(Token token);

    Optional<Token> getToken(String token);

    Token getCofirmedAt(Long id);

    int setConfirmedAt(String token);

    List<Token> findAllValidTokenByUser(Long id);

    List<Token> findAllToken(Long id);

    void deleteAllTokenByUserId(Long id);

    List<Token> saveAll(List<Token> tokens);

}
