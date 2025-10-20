package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.ParticipantagDto;

public interface ParticipantagService {

    ParticipantagDto create(ParticipantagDto participantag);

    ParticipantagDto update(ParticipantagDto participantag);

    ParticipantagDto read(Long id);

    void delete(Long id);

    Page<ParticipantagDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<ParticipantagDto> readByAssemblegeneralId(Long assemblegeneralId);

}