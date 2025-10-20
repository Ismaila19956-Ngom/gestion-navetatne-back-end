package com.webgram.dgpsn.services.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.models.responses.SignInAuthentication;
import com.webgram.dgpsn.repositories.UserRepository;
import com.webgram.dgpsn.security.jwt.TokenProvider;
import com.webgram.dgpsn.services.AuthenticationService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    static final String TOKEN_TYPE ="Bearer";

    final AuthenticationManagerBuilder authenticationManagerBuilder;

    final TokenProvider tokenProvider;

    final UserRepository userRepository;

    static final String ACCOUNT_LOCKED = "Ce compte est bloqué: {0}";

    @Override
    public SignInAuthentication signIn(String username, String password) {
       /* var user = userRepository.findByLogin(username);
        /*if(user.isPresent() && user.get().getStatus() == false) {
            throw new UserNotActivatedException(MessageFormat.format(ACCOUNT_LOCKED, username));
        } */
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                password
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createToken(authentication, true);
        // start update users online state
        var user = userRepository.findByLogin(username).orElseThrow();
        user.setOnline(Boolean.TRUE);
        user.setLastConnexion(LocalDateTime.now());
        userRepository.save(user);
        // end update users online state
        return SignInAuthentication
                .builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(accessToken)
                .build();

    }

    @Override
    public void signOut(String username) {
        var user = userRepository.findByLogin(username).orElseThrow();
        user.setOnline(Boolean.FALSE);
        userRepository.save(user);
    }
}
