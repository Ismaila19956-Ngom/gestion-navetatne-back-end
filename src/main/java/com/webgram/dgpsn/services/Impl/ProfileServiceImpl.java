package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.exceptions.ResourceAlreadyExistException;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ProfileMapper;
import com.webgram.dgpsn.models.ProfileDTO;
import com.webgram.dgpsn.repositories.ProfileRepository;
import com.webgram.dgpsn.services.ProfileService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public ProfileDTO create(ProfileDTO profileDTO) {
        var profileToUpdate = profileRepository.findByCode(profileDTO.getCode());
        if(profileToUpdate.isPresent()) {
            throw new ResourceAlreadyExistException("code profil existe déjà");
        }
        var savedProfile = profileRepository.save(profileMapper.asEntity(profileDTO));

        log.info("Profile successfully added {}", savedProfile);

        return profileMapper.asDto(savedProfile);
    }

    @Override
    public ProfileDTO update(ProfileDTO profileDTO) {
        try{
            if(profileRepository.existsById(profileDTO.getId())) {
                var profile = profileMapper.asEntity(profileDTO);

                var updatedProfile = profileMapper.asDto(profileRepository.save(profile));

                log.info("Profile successfully updated {} ", profile.getId());

                return updatedProfile;
            } else {
                throw new ResourceNotFoundException("Profile", profileDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Profile", profileDTO.getId());
        }
    }

    @Override
    public ProfileDTO read(Long profileId) {
        var profile = profileRepository
                .findById(profileId)
                .orElseThrow(()-> new ResourceNotFoundException("Profile", profileId));

        log.info("reading profile id {}", profileId);

        return profileMapper.asDto(profile);
    }

    @Override
    public void delete(Long profileId) {
        try {
            profileRepository.deleteById(profileId);
            log.info("The profile id {} is deleted", profileId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Profile", profileId);
        }
    }

    @Override
    public Page<ProfileDTO> readAll(
            Pageable pageable,
            String code,
            String libelle
    ) {
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("libelle").ascending());
        return profileRepository
                .readAllByFilters(pageRequest, code, libelle)
                .map(profileMapper::asDto);
    }
}
