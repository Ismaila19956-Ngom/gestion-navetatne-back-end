package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.responses.SignInAuthentication;

public interface AuthenticationService {
    SignInAuthentication signIn(String username, String password);
    void signOut(String username);
}
