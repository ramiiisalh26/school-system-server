package com.example.school.security.config;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    
    
    @Value("${jwt.public.key}")
    private String publicKey;

    @Value("${jwt.private.key}")
    private String privateKey;

    @Bean
    public RSAPublicKey publicKey() throws Exception {
        return parsePublicKey(publicKey);
    }

    @Bean
    public RSAPrivateKey privateKey() throws Exception {
        return parsePrivateKey(privateKey);
    }

    @Bean
    public JwtEncoder jwtEncoder() throws Exception{
        // Creates a new RSA key using the provided public and private RSA keys.
        var jwk = new RSAKey.Builder(publicKey()).privateKey(privateKey()).build();

        // Wraps the RSA key in a JSON Web Key Set (JWKSet), making it immutable.
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        // Uses the Nimbus library to create a JWT encoder that will sign tokens with the RSA private key.
        return new NimbusJwtEncoder(jwks);
    }


    @Bean // This bean is responsible for decoding (verifying) JWT tokens.
    public JwtDecoder jwtDecoder() throws Exception{ 
        // Creates a JWT decoder using the RSA public key, which is used to verify the signature of JWT tokens.
        return NimbusJwtDecoder.withPublicKey(publicKey()).build();
    }

     // Utility to parse PEM public key
    private RSAPublicKey parsePublicKey(String key) throws Exception {
        String publicKeyPEM = key.replace("-----BEGIN PUBLIC KEY-----", "")
                                 .replace("-----END PUBLIC KEY-----", "")
                                 .replaceAll("\\s", "");
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    // Utility to parse PEM private key
    private RSAPrivateKey parsePrivateKey(String key) throws Exception {
        String privateKeyPEM = key.replace("-----BEGIN PRIVATE KEY-----", "")
                                  .replace("-----END PRIVATE KEY-----", "")
                                  .replaceAll("\\s", "");
        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}
