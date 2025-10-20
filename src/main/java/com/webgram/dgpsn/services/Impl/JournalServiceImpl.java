package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.mappers.JournalMapper;
import com.webgram.dgpsn.models.JournalDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.JournalService;

import java.time.LocalDateTime;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;

    private final JournalMapper journalMapper;


    @Override
    public Page<JournalDTO> readAll(Pageable pageable, String user, String login, String actionType, LocalDateTime creationDate, String critere, LocalDateTime endDate) {
        var pageableshort = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("creationDate").descending());
        var journals = journalRepository.readAllByFilters(pageableshort, user, login, actionType, creationDate, critere, endDate)
                .map(journalMapper::asDto);
        log.trace("list journal get ok {}", journals);
        return journals;

    }

}
