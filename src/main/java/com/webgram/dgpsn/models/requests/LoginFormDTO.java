package com.webgram.dgpsn.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormDTO implements Serializable {
    private String login;
    private String password;
}
