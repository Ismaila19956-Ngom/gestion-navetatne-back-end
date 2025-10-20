package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QQueryEntity;
import com.webgram.dgpsn.entities.QueryEntity;
import com.webgram.dgpsn.entities.enums.TypeApplicant;

import java.util.Date;
import java.util.Objects;

@Repository
public interface QueryRepository extends JpaRepository<QueryEntity, Long>, QuerydslPredicateExecutor<QueryEntity> {

    default Page<QueryEntity> readAllByFilters(
            Pageable pageable,
               Date date,
               TypeApplicant typeDemandeur,
               TypeApplicant typeDestinataire,
               Long categorieRequeteId,
               Long typeRequeteId,
               Long demandeurActorId,
               Long demandeurStructureId,
               Long destinataireActorId,
               Long destinataireStructureId,
               Long projetId
    ) {
        var booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(date)){
            booleanBuilder.and(QQueryEntity.queryEntity.date.eq(date));
        }
        if(Objects.nonNull(typeDemandeur)){
            booleanBuilder.and(QQueryEntity.queryEntity.typeDemandeur.eq(typeDemandeur));
        }
        if(Objects.nonNull(typeDestinataire)){
            booleanBuilder.and(QQueryEntity.queryEntity.typeDestinataire.eq(typeDestinataire));
        }
        if(Objects.nonNull(categorieRequeteId)){
            booleanBuilder.and(QQueryEntity.queryEntity.categorieRequete.id.eq(categorieRequeteId));
        }
        if(Objects.nonNull(typeRequeteId)){
            booleanBuilder.and(QQueryEntity.queryEntity.typeRequete.id.eq(typeRequeteId));
        }
        if(Objects.nonNull(demandeurActorId)){
            booleanBuilder.and(QQueryEntity.queryEntity.demandeurActor.id.eq(demandeurActorId));
        }
        if(Objects.nonNull(demandeurStructureId)){
            booleanBuilder.and(QQueryEntity.queryEntity.demandeurStructure.id.eq(demandeurStructureId));
        }
        if(Objects.nonNull(destinataireActorId)){
            booleanBuilder.and(QQueryEntity.queryEntity.destinataireActor.id.eq(destinataireActorId));
        }
        if(Objects.nonNull(destinataireStructureId)){
            booleanBuilder.and(QQueryEntity.queryEntity.destinataireStructure.id.eq(destinataireStructureId));
        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QQueryEntity.queryEntity.projet.id.eq(projetId));
        }

        return findAll(booleanBuilder, pageable);
    }
}
