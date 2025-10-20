package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ContactRequestEntity;
import com.webgram.dgpsn.entities.QContactRequestEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.TypeDemande;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface ContactRequestRepository extends JpaRepository<ContactRequestEntity, Long>, QuerydslPredicateExecutor<ContactRequestEntity> {

    Optional<ContactRequestEntity> findByEmailAndSubject(String email, String subject);

    default Page<ContactRequestEntity> readAllByFiltering(
            Pageable pageable,
            List<Long> idsToIgnore,
            TypeDemande requestType,
            String lastName,
            String firstName,
            String email,
            String phone,
            String organization,
            String subject,
            Statut statut,
            Date dateCreation,
            Long serviceId,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if (Objects.nonNull(idsToIgnore)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.id.notIn(idsToIgnore));
        }
        if (Objects.nonNull(requestType)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.requestType.eq(requestType));
        }
        if (StringUtils.isNotEmpty(lastName)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.lastName.containsIgnoreCase(lastName));
        }
        if (StringUtils.isNotEmpty(firstName)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.firstName.containsIgnoreCase(firstName));
        }
        if (StringUtils.isNotEmpty(email)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.email.containsIgnoreCase(email));
        }
        if (StringUtils.isNotEmpty(phone)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.phone.containsIgnoreCase(phone));
        }
        if (StringUtils.isNotEmpty(organization)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.organization.containsIgnoreCase(organization));
        }
        if (Objects.nonNull(statut)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.statut.eq(statut));
        }
        if (Objects.nonNull(dateCreation)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.dateCreation.eq(dateCreation));
        }
        if (StringUtils.isNotEmpty(subject)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.subject.containsIgnoreCase(subject));
        }
        if (Objects.nonNull(serviceId)) {
            booleanBuilder.and(QContactRequestEntity.contactRequestEntity.service.id.eq(serviceId));
        }
        if (StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if (Objects.nonNull(ascending)) {
            sort.ascending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuilder, pageRequest);
    }
}