package com.webgram.dgpsn.security.jwt;

import com.webgram.dgpsn.configurations.SecurityMetersConfiguration;
import com.webgram.dgpsn.exceptions.UserDisabledException;
import com.webgram.dgpsn.properties.JwtProperties;
import com.webgram.dgpsn.repositories.UserRepository;
import com.webgram.dgpsn.security.CustomUserDetails;
import com.webgram.dgpsn.utils.HttpRequestResponseUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String PROFILE_KEY = "profile";
    private static final String USER_AGENT = "user_agent";

    private final Key key;

    private final JwtParser jwtParser;

    private final long tokenValidityInMilliseconds;

    private final long tokenValidityInMillisecondsForRememberMe;

    private final SecurityMetersConfiguration securityMetersConfiguration;

    static final String ACCOUNT_LOCKED = "Ce compte est bloqué: {0}";

    private final UserRepository userRepository;

    public TokenProvider(JwtProperties jwtProperties, SecurityMetersConfiguration securityMetersConfiguration, UserRepository userRepository) {
        this.userRepository = userRepository;
        byte[] keyBytes;
        String secret = jwtProperties.getBase64Secret();
        if (!ObjectUtils.isEmpty(secret)) {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(secret);
        } else {
            log.warn(
                    "Warning: the JWT key used is not Base64-encoded. " +
                            "We recommend using the `jhipster.security.authentication.jwt.base64-secret` key for optimum security."
            );
            secret = jwtProperties.getSecret();
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = 1000 * jwtProperties.getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
                1000 * jwtProperties.getTokenValidityInSecondsForRememberMe();

        this.securityMetersConfiguration = securityMetersConfiguration;
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        var nameAgent = "";
        var nameFonction = "";
        var profileLibelle = "";
        var userConnected = userRepository.findByLogin(authentication.getName());
        Long userId = null; // Initialisation de l'ID utilisateur
        if (userConnected.isPresent()) {
            var user = userConnected.get();
            userId = user.getId(); // Récupération de l'ID utilisateur
            // Vérification et récupération du prénom et du nom de l'agent
            var agent = user.getAgent();
            if (agent != null) {
                String prenom = agent.getPrenom() != null ? agent.getPrenom() : "";
                String nom = agent.getNom() != null ? agent.getNom() : "";
                nameAgent = prenom + " " + nom;

                // Vérification et récupération de la fonction
//                var fonction = agent.getFonction();
//                if (fonction != null && fonction.getLibelle() != null) {
//                    nameFonction = fonction.getLibelle();
//                }
            }

            // Vérification et récupération du libellé du profil
            var profile = user.getProfile();
            if (profile != null && profile.getLibelle() != null) {
                profileLibelle = profile.getLibelle();
            }
        }
        String profile = userRepository.findByLogin(authentication.getName()).map(userEntity -> userEntity.getProfile().getCode()).orElse("");
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(PROFILE_KEY, profile)
                .claim(USER_AGENT, HttpRequestResponseUtils.getUserAgent())
                .claim("nameAgent", nameAgent)
                .claim("nameFonction", nameFonction)
                .claim("profileLibelle", profileLibelle)
                .claim("userId", userId)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        String userAgent = (String) claims.get(USER_AGENT);
        Integer userId = (Integer) claims.get("userId");

        if (Objects.isNull(userAgent) || !userAgent.equals(HttpRequestResponseUtils.getUserAgent())) {
//            throw new UsernameNotFoundException("Token not valid");
            throw new BadCredentialsException("Invalid credentials");
        }

//        User principal = new User(claims.getSubject(), "", authorities);
        User principal = new CustomUserDetails(userId.longValue(), claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException e) {
            this.securityMetersConfiguration.trackTokenExpired();
            log.trace("Invalid JWT token.", e);
           /* throw new ExpiredJwtException("le token est expire");*/

        } catch (UnsupportedJwtException e) {
            this.securityMetersConfiguration.trackTokenUnsupported();

            log.trace("Invalid JWT token.", e);
        } catch (MalformedJwtException e) {
            this.securityMetersConfiguration.trackTokenMalformed();

            log.trace("Invalid JWT token.", e);
        } catch (SignatureException e) {
            this.securityMetersConfiguration.trackTokenInvalidSignature();

            log.trace("Invalid JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.error("Token validation error {}", e.getMessage());
        }

        return false;
    }

    public void checkStatus(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        var username = claims.getSubject();
        var userEntity = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User {0}  was not found in the database", username)));

        if(!userEntity.getStatus()) {
            throw new UserDisabledException(MessageFormat.format(ACCOUNT_LOCKED, username));
        }
    }
}
