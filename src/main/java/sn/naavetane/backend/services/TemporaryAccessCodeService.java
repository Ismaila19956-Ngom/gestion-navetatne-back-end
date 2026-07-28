package sn.naavetane.backend.services;// package sn.webg.suivievaluation.services;

import sn.naavetane.backend.models.TemporaryAccessCodeDTO;
import sn.naavetane.backend.models.responses.SignInAuthentication;

import java.util.List;

public interface TemporaryAccessCodeService {
    String generateTemporaryCode(String username, int validityInMinutes);
    SignInAuthentication signInWithTemporaryCode(String code);
    List<TemporaryAccessCodeDTO> getTemporaryCodes(String username);
    TemporaryAccessCodeDTO updateCodeStatus(String code, boolean active);

}
