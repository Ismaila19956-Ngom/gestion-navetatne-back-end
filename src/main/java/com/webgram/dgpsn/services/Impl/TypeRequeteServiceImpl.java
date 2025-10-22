package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.TypeRequeteMapper;
import com.webgram.dgpsn.models.TypeRequeteDTO;
import com.webgram.dgpsn.repositories.TypeRequeteRepository;
import com.webgram.dgpsn.services.TypeRequeteService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TypeRequeteServiceImpl implements TypeRequeteService {
    private final TypeRequeteRepository expenseRepository;
    private final TypeRequeteMapper typeRequeteMapper;

    @Override
    @Journal(actionType= ActionType.ADD_INDICATEURS_TO_AVANCEMENT)
    public TypeRequeteDTO create(TypeRequeteDTO typeRequeteDTO) {
         var savedTypeRequete = expenseRepository.save(typeRequeteMapper.asEntity(typeRequeteDTO));

        log.info("TypeRequete successfully added {}", savedTypeRequete);

        return typeRequeteMapper.asDto(savedTypeRequete);
    }

    @Override
    @Journal(actionType=ActionType.UPDATE_INDICATEURS_TO_AVANCEMENT)
    public TypeRequeteDTO update(TypeRequeteDTO typeRequeteDTO) {
        try{
            if(expenseRepository.existsById(typeRequeteDTO.getId())) {
                var indicator = typeRequeteMapper.asEntity(typeRequeteDTO);

                var updatedRequete = expenseRepository.save(indicator);

                log.info("Requete successfully updated {} ", updatedRequete.getId());

                return typeRequeteMapper.asDto(updatedRequete);
            } else {
                throw new ResourceNotFoundException("Requete", typeRequeteDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Requete", typeRequeteDTO.getId());
        }
    }

    @Override
    @Journal(actionType=ActionType.READ_INDICATEURS_TO_AVANCEMENT)
    public TypeRequeteDTO read(Long expenseId) {
        var Requete = expenseRepository
                .findById(expenseId)
                .orElseThrow(()-> new ResourceNotFoundException("Requete", expenseId));

        log.info("reading Requete id {}", expenseId);

        return typeRequeteMapper.asDto(Requete);
    }

    @Override
    @Journal(actionType=ActionType.DELETE_INDICATEURS_TO_AVANCEMENT)
    public void delete(Long expenseId) {
        try {
            expenseRepository.deleteById(expenseId);
            log.info("The expense id {} is deleted", expenseId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("expenseId", expenseId);
        }
    }

    @Override
    @Journal(actionType=ActionType.READ_INDICATEURS_TO_AVANCEMENT)
    public Page<TypeRequeteDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            Long categorieRequeteId,
            String sortBy,
            Boolean ascending
    ) {
        return expenseRepository
                .readAllByFilters(pageable, code, libelle, categorieRequeteId,sortBy,
                         ascending)
                .map(typeRequeteMapper::asDto);
    }
}
