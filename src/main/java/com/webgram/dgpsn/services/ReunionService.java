package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.models.ReunionDto;

public interface ReunionService {

    ReunionDto create(ReunionDto reunion);

    ReunionDto update(ReunionDto reunion);

    ReunionDto read(Long id);

    void delete(Long id);

    Page<ReunionDto> readAll(Map<String, String> searchParams, Pageable pageable);

    ReunionDto changeStatus(Long reunionId, Statut statut);
    List<ReunionDto> readByConseiladministratifId(Long conseiladministratifId);

}