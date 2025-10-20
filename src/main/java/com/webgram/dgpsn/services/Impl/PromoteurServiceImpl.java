package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PromoteurMapper;
import com.webgram.dgpsn.models.PromoteurDTO;
import com.webgram.dgpsn.repositories.PromoteurRepository;
import com.webgram.dgpsn.services.PromoteurService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PromoteurServiceImpl implements PromoteurService {

    private final PromoteurRepository promoteurRepository;
    private final PromoteurMapper promoteurMapper;

    @Override
    public PromoteurDTO create(PromoteurDTO promoteur) {
        var savedPromoteur = promoteurRepository.save(promoteurMapper.asEntity(promoteur));
        log.info("promoteur successfully added {}", savedPromoteur);
        return promoteurMapper.asDto(savedPromoteur);
    }

    @Override
    public PromoteurDTO update(PromoteurDTO promoteurDTO) {
        var promoteur = promoteurMapper.asEntity(promoteurDTO);
        var updatedPromoteur = promoteurRepository.save(promoteur);
        log.info("promoteur successfully updated {} ", updatedPromoteur.getId());
        return promoteurMapper.asDto(updatedPromoteur);
    }

    @Override
    public PromoteurDTO read(Long promoteurId) {
        var promoteur = promoteurRepository
                .findById(promoteurId)
                .orElseThrow(()-> new ResourceNotFoundException("promoteur", promoteurId));

        log.info("reading promoteur id {}", promoteurId);

        return promoteurMapper.asDto(promoteur);
    }

    @Override
    public void delete(Long promoteurId) {
        try {
            promoteurRepository.deleteById(promoteurId);
            log.info("The promoteur id {} is deleted", promoteurId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<PromoteurDTO> readAll(Pageable pageable, String nomEntreprise, String personneContact, String fonctionContact, String telephone, String bureauEtudes, String adresseSiege, String adresseSite) {
        return promoteurRepository
                .readAllByFilters(pageable, nomEntreprise, personneContact, fonctionContact, telephone, bureauEtudes, adresseSiege, adresseSite)
                .map(promoteurMapper::asDto);
    }
}
