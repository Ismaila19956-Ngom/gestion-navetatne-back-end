package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PassationMarketCritereMapper;
import com.webgram.dgpsn.models.PassationMarketCritereDTO;
import com.webgram.dgpsn.repositories.PassationMarketCritereRepository;
import com.webgram.dgpsn.services.PassationMarketCritereService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PassationMarketCritereServiceImpl implements PassationMarketCritereService {

    private static final String STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE = "id n'existe pas";
    private final PassationMarketCritereRepository passationMarketCritereRepository;
    private final PassationMarketCritereMapper passationMarketCritereMapper;

    @Override
    public List<PassationMarketCritereDTO> readAllCriterePerMarket(long id) {
        var passationMarket = passationMarketCritereRepository
                .findByPassationMarketEntityId(id)
                .orElseThrow(()-> new ResourceNotFoundException("le marche n'existe", id));

        log.info("reading Passation de Plan id {}", id);

       return passationMarketCritereMapper.parse(passationMarket);
    }

    @Override
    public List<PassationMarketCritereDTO> createPassationMarketCritere(List<PassationMarketCritereDTO> passationMarketCritereDTO) {
        var savedpassationMarketCritere = passationMarketCritereRepository.saveAll(passationMarketCritereMapper.parseToEntity(passationMarketCritereDTO));

        log.info("Structure successfully added {}", savedpassationMarketCritere);

        return passationMarketCritereMapper.parse(savedpassationMarketCritere);
    }

    @Override
    public PassationMarketCritereDTO updatePassationMarketCritere(PassationMarketCritereDTO passationMarketCritereDTO) {
        if(!passationMarketCritereRepository.existsById(passationMarketCritereDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE, passationMarketCritereDTO.getId()));
        }

        var passationMarketCriter = passationMarketCritereMapper.asEntity(passationMarketCritereDTO);

        var updatedpassationMarketCriter = passationMarketCritereMapper.asDto(passationMarketCritereRepository.save(passationMarketCriter));

        log.info("Structure successfully updated {} ", passationMarketCriter.getId());

        return updatedpassationMarketCriter;
    }

    @Override
    public PassationMarketCritereDTO readPassationMarketCritere(Long id) {
        var passationMarket = passationMarketCritereRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("le marche n'existe", id));

        log.info("reading Passation de Plan id {}", id);

        return passationMarketCritereMapper.asDto(passationMarket);
    }

    @Override
    public void deletePassationMarketCritere(Long id) {
        try {
            passationMarketCritereRepository.deleteById(id);
            log.info("The plan of passation market id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<PassationMarketCritereDTO> readAllPassationMarketCritere(Pageable pageable, Double ponderation, String expectedValue,Long passationMarketId, String sortBy, Boolean ascending) {
        return passationMarketCritereRepository.readAllByFiltering(pageable,ponderation,expectedValue,passationMarketId,sortBy,ascending)
                .map(passationMarketCritereMapper::asDto);
    }
}
