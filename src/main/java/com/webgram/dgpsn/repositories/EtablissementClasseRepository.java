package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.EtablissementClasseEntity;
import com.webgram.dgpsn.entities.QEtablissementClasseEntity;
import com.webgram.dgpsn.models.responses.DataPoint;

import java.util.List;
import java.util.Objects;

@Repository
public interface EtablissementClasseRepository extends JpaRepository<EtablissementClasseEntity, Long>, QuerydslPredicateExecutor<EtablissementClasseEntity> {

    default Page<EtablissementClasseEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            String libelle,
            String latitude,
            String longitude,
            String contactPerson,
            String contactRole,
            String contactInfo,
            String mainActivity,
            Long typeEtablissementId,
            Long regionId,
            Long departementId,
            Long categoryICPEId,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if (Objects.nonNull(code)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.code.containsIgnoreCase(code));
        }
        if (Objects.nonNull(libelle)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.libelle.containsIgnoreCase(libelle));
        }
        if (Objects.nonNull(latitude)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.latitude.containsIgnoreCase(latitude));
        }
        if (Objects.nonNull(longitude)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.longitude.containsIgnoreCase(longitude));
        }
        if (Objects.nonNull(contactPerson)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.contactPerson.containsIgnoreCase(contactPerson));
        }
        if (Objects.nonNull(contactRole)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.contactRole.containsIgnoreCase(contactRole));
        }
        if (Objects.nonNull(contactInfo)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.contactInfo.containsIgnoreCase(contactInfo));
        }
        if (Objects.nonNull(mainActivity)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.mainActivity.containsIgnoreCase(mainActivity));
        }
        if (Objects.nonNull(typeEtablissementId)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.typeEtablissement.id.eq(typeEtablissementId));
        }
        if (Objects.nonNull(regionId)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.region.id.eq(regionId));
        }
        if (Objects.nonNull(departementId)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.departement.id.eq(departementId));
        }
        if (Objects.nonNull(categoryICPEId)) {
            booleanBuilder.and(QEtablissementClasseEntity.etablissementClasseEntity.categoryICPE.id.eq(categoryICPEId));
        }
        if (StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if (Objects.nonNull(ascending) && ascending) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuilder, pageRequest);
    }

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(c.libelle, COUNT(e)) " +
            "FROM EtablissementClasseEntity e JOIN e.categoryICPE c " +
            "GROUP BY c.libelle")
    List<DataPoint<String, Long>> countByCategory();
}