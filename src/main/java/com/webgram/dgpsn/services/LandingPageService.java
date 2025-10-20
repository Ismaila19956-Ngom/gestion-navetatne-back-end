package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.landingPage.KeysDataDTO;
import com.webgram.dgpsn.models.landingPage.ProjectFundingDTO;

import java.util.List;

public interface LandingPageService {
    KeysDataDTO getKeysData();
    List<ProjectFundingDTO> getAverageFundingByProject();
}
