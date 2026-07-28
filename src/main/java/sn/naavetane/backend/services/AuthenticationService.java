package sn.naavetane.backend.services;

import sn.naavetane.backend.models.responses.SignInAuthentication;

import sn.naavetane.backend.models.requests.SignUpDTO;

public interface AuthenticationService {
    SignInAuthentication signIn(String username, String password);
    SignInAuthentication signUp(SignUpDTO signUpDTO);
    void signOut(String username);
}
