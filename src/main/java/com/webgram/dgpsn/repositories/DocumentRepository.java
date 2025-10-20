package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.DocumentEntity;
import com.webgram.dgpsn.entities.QDocumentEntity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long>, QuerydslPredicateExecutor<DocumentEntity> {

    default Page<DocumentEntity> readAllByFilters(Pageable pageable,String libelle, Long projetId,Long entrepriseId, Long documentTypeId, String author,String date) throws ParseException {
        var booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QDocumentEntity.documentEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QDocumentEntity.documentEntity.projet.id.eq(projetId));
        }
        if(Objects.nonNull(entrepriseId)){
            booleanBuilder.and(QDocumentEntity.documentEntity.entreprise.id.eq(entrepriseId));
        }
        if(Objects.nonNull(documentTypeId)){
            booleanBuilder.and(QDocumentEntity.documentEntity.documentType.id.eq(documentTypeId));
        }
        if(StringUtils.isNotEmpty(author)) {
            booleanBuilder.and(QDocumentEntity.documentEntity.author.eq(author));
        }
        if(Objects.nonNull(date)) {
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            booleanBuilder.and(QDocumentEntity.documentEntity.date.eq(formatStartDate));
        }
        return findAll(booleanBuilder, pageable);
    }
}
