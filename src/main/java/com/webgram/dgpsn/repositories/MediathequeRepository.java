package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.enums.MediathequeType;
import com.webgram.dgpsn.entities.MediathequeEntity;
import com.webgram.dgpsn.entities.QMediathequeEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Repository
public interface MediathequeRepository extends JpaRepository<MediathequeEntity, Long>, QuerydslPredicateExecutor<MediathequeEntity> {

    default Page<MediathequeEntity> readAllByFilters(
            Pageable pageable,
            String libelle,
            String date,
            MediathequeType mediathequeType,
            Long projetId,
             Long entrepriseId
    ) throws ParseException {
        var booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(libelle)){
            booleanBuilder.and(QMediathequeEntity.mediathequeEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(date)){
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            booleanBuilder.and(QMediathequeEntity.mediathequeEntity.date.eq(formatStartDate));
        }
        if(Objects.nonNull(mediathequeType)) {
            booleanBuilder.and((QMediathequeEntity.mediathequeEntity.mediathequeType.eq(mediathequeType)));
        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QMediathequeEntity.mediathequeEntity.projet.id.eq(projetId));
        }
        if(Objects.nonNull(entrepriseId)){
            booleanBuilder.and(QMediathequeEntity.mediathequeEntity.entreprise.id.eq(entrepriseId));
        }
        return findAll(booleanBuilder, pageable);
    }
}
