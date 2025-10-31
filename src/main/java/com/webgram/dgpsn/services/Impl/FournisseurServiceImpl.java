package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.FournisseurMapper;
import com.webgram.dgpsn.models.FournisseurDTO;
import com.webgram.dgpsn.repositories.FournisseurRepository;
import com.webgram.dgpsn.services.FournisseurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Service @Transactional @RequiredArgsConstructor @Slf4j // Annotations similaires à [42]
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;

    private static final String FOURNISSEUR_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id fournisseur: {}";

    @Override
    public FournisseurDTO create(FournisseurDTO fournisseurDTO) {
        var savedFournisseur = fournisseurRepository.save(fournisseurMapper.asEntity(fournisseurDTO));
        log.info("fournisseur successfully added {}", savedFournisseur);
        return fournisseurMapper.asDto(savedFournisseur);
    }


    @Override
    public FournisseurDTO update(Long fournisseurId, FournisseurDTO fournisseurDTO) {
        if (!fournisseurRepository.existsById(fournisseurId)) {
            throw new ResourceNotFoundException("Fournisseur", fournisseurId);
        }
        var fournisseur = fournisseurMapper.asEntity(fournisseurDTO);
        fournisseur.setId(fournisseurId); // Assurer que l'ID est maintenu pour la mise à jour
        var updatedFournisseur = fournisseurMapper.asDto(fournisseurRepository.save(fournisseur));
        log.info("fournisseur successfully updated {} ", updatedFournisseur.getId());
        return updatedFournisseur;
    }

    @Override
    public FournisseurDTO read(Long fournisseurId) {
        var fournisseur = fournisseurRepository
                .findById(fournisseurId)
                .orElseThrow(()-> new ResourceNotFoundException("Fournisseur", fournisseurId));
        log.info("reading fournisseur id {}", fournisseurId);
        return fournisseurMapper.asDto(fournisseur);
    }

    @Override
    public void delete(Long fournisseurId) {
        try {
            fournisseurRepository.deleteById(fournisseurId);
            log.info("The fournisseur id {} is deleted", fournisseurId);
        } catch (IllegalArgumentException ex) {
            log.warn("The given id to delete must not be null");
        }
    }

    @Override
    public Page<FournisseurDTO> readAll(
            Pageable pageable,
            String raisonSociale,
            String codeFournisseur,
            String ninea,
            String categorieFournisseur,
            String statut,
            String sortBy,
            Boolean ascending
    ) {
        return fournisseurRepository
                .readAllByFiltering(pageable, raisonSociale, codeFournisseur, ninea, categorieFournisseur, statut, sortBy, ascending)
                .map(fournisseurMapper::asDto);
    }
}