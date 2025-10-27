package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.QLabelEntity;
import com.webgram.dgpsn.entities.enums.ReferentielType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, Long>, QuerydslPredicateExecutor<LabelEntity> {

    default Page<LabelEntity> readByFiltering(Pageable pageable, ReferentielType referentielType, String label) {
        var booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(referentielType)) {
            booleanBuilder.and(QLabelEntity.labelEntity.referentielType.eq(referentielType));
        }
        if(StringUtils.isNotEmpty(label)) {
            booleanBuilder.and(QLabelEntity.labelEntity.libelle.eq(label));
        }

        return findAll(booleanBuilder, pageable);
    }

    Optional<LabelEntity> findByCode(String code);

    @Query("SELECT l FROM LabelEntity l WHERE l.id IN :ids")
    Set<LabelEntity> findAllByIds(@Param("ids") List<Long> ids);

    // Ajout des méthodes manquantes pour le système de courriers
    List<LabelEntity> findByReferentielType(ReferentielType referentielType);

    @Query("SELECT l FROM LabelEntity l WHERE l.referentielType = :referentielType AND l.code = :code")
    Optional<LabelEntity> findByTypeAndCode(@Param("referentielType") ReferentielType referentielType, @Param("code") String code);

}
