package com.webgram.dgpsn.services;// package sn.webg.suivievaluation.services;

import com.webgram.dgpsn.models.TemporaryAccessCodeDTO;
import com.webgram.dgpsn.models.responses.SignInAuthentication;

import java.util.List;

public interface TemporaryAccessCodeService {
    String generateTemporaryCode(String username, int validityInMinutes);
    SignInAuthentication signInWithTemporaryCode(String code);
    List<TemporaryAccessCodeDTO> getTemporaryCodes(String username);
    TemporaryAccessCodeDTO updateCodeStatus(String code, boolean active);

}