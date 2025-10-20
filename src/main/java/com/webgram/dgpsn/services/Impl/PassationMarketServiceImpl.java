package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PassationMarketMapper;
import com.webgram.dgpsn.models.PassationMarketDTO;
import com.webgram.dgpsn.repositories.PassationMarketRepository;
import com.webgram.dgpsn.services.PassationMarketService;

import java.text.MessageFormat;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PassationMarketServiceImpl implements PassationMarketService {

    private static final String STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE = "id n'existe pas";
    private  final PassationMarketRepository passationMarketRepository;
    private  final PassationMarketMapper passationMarketMapper;

    @Override
    public PassationMarketDTO createPassationMarket(PassationMarketDTO passationMarketDTO) {
        var savedpassationMarket = passationMarketRepository.save(passationMarketMapper.asEntity(passationMarketDTO));

        log.info("Structure successfully added {}", savedpassationMarket);

        return passationMarketMapper.asDto(savedpassationMarket);
    }

    @Override
    public PassationMarketDTO updatePassationMarket(PassationMarketDTO passationMarketDTO) {
        if(!passationMarketRepository.existsById(passationMarketDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE, passationMarketDTO.getId()));
        }

        var passation = passationMarketMapper.asEntity(passationMarketDTO);

        var updatedpassation = passationMarketMapper.asDto(passationMarketRepository.save(passation));

        log.info("Structure successfully updated {} ", updatedpassation.getId());

        return updatedpassation;
    }

    @Override
    public PassationMarketDTO readPassationMarket(Long id) {
        var passationMarket = passationMarketRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("le marche n'existe", id));

        log.info("reading Passation de Plan id {}", id);

        return passationMarketMapper.asDto(passationMarket);
    }

    @Override
    public void deletePassationMarket(Long id) {
        try {
            passationMarketRepository.deleteById(id);
            log.info("The plan of passation market id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<PassationMarketDTO> readAllPassationMarket(Pageable pageable, String reference, String libelle,Long passationPlanId, String sortBy, Boolean ascending) {
        return passationMarketRepository.readAllByFiltering(pageable,reference,libelle,passationPlanId,sortBy,ascending)
                .map(passationMarketMapper::asDto);
    }
}
