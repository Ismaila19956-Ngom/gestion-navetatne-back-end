package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.ProfileDTO;


public interface ProfileService {
    ProfileDTO create(ProfileDTO profileDTO);
    ProfileDTO update(ProfileDTO profileDTO);
    ProfileDTO read(Long profileId);
    void delete(Long profileId);
    Page<ProfileDTO> readAll(
            Pageable pageable,
            String code,
            String libelle
    );
}
