package com.example.school.security.token;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenServicesImpl implements ITokenServices{

    private final ITokenRepository tokenRepository;

    @Override
    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public Token getCofirmedAt(Long id){
        return tokenRepository.getCofirmedAt(id);
    }

    @Override
    public int setConfirmedAt(String token) {
        return tokenRepository.updateConfirmedAt(token, Instant.now());
    }

    @Override
    public List<Token> findAllValidTokenByUser(Long id) {
        return tokenRepository.findAllValidToken(id);
    }

    @Override
    public List<Token> findAllToken(Long id){
        return tokenRepository.findAllToken(id);
    }

    @Override
    public List<Token> saveAll(List<Token> tokens) {
        return tokenRepository.saveAll(tokens);
    }

    @Override
    public void deleteAllTokenByUserId(Long id){
        tokenRepository.deleteAllTokenByUserId(id);
    }
    
}
