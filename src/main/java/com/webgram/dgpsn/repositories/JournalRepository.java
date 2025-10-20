package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.webgram.dgpsn.entities.JournalEntity;
import com.webgram.dgpsn.entities.QJournalEntity;

import java.time.LocalDateTime;
import java.util.Objects;

public interface JournalRepository extends JpaRepository<JournalEntity, Long>, QuerydslPredicateExecutor<JournalEntity> {
    default Page<JournalEntity> readAllByFilters(Pageable pageable, String user, String login, String actionType, LocalDateTime creationDate, String critere, LocalDateTime endDate) {
        var booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(user)) {
            booleanBuilder.and(QJournalEntity.journalEntity.user.containsIgnoreCase(user));
        }
        if(Objects.nonNull(login)) {
            booleanBuilder.and(QJournalEntity.journalEntity.login.eq(login));
        }
        if(Objects.nonNull(actionType)) {
            booleanBuilder.and(QJournalEntity.journalEntity.actionType.containsIgnoreCase(actionType));
        }
        if(Objects.nonNull(creationDate) && StringUtils.isNotEmpty(critere)){
            if("Egal".equals(critere)) {
                booleanBuilder.and(QJournalEntity.journalEntity.creationDate.goe(creationDate));
                booleanBuilder.and(QJournalEntity.journalEntity.creationDate.loe(endDate));
            }else if ("Inferieure".equals(critere)) {
                booleanBuilder.and(QJournalEntity.journalEntity.creationDate.loe(creationDate));
            }else if ("Supperieure".equals(critere)) {
                booleanBuilder.and(QJournalEntity.journalEntity.creationDate.goe(creationDate));
            }else {
                if(Objects.nonNull(endDate)) {
                    booleanBuilder.and(QJournalEntity.journalEntity.creationDate.goe(creationDate));
                    booleanBuilder.and(QJournalEntity.journalEntity.creationDate.loe(endDate));
                }else {
                    booleanBuilder.and(QJournalEntity.journalEntity.creationDate.goe(creationDate));
                }

            }

        }
        return findAll(booleanBuilder, pageable);
    }
}
