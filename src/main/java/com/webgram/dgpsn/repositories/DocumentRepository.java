package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.DocumentEntity;
import com.webgram.dgpsn.entities.QDocumentEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.text.ParseException;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long>, QuerydslPredicateExecutor<DocumentEntity> {


    default Page<DocumentEntity> readAllByFilters(Pageable pageable, String libelle, String author) throws ParseException {
        var booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QDocumentEntity.documentEntity.libelle.containsIgnoreCase(libelle));
        }

        if(StringUtils.isNotEmpty(author)) {
            booleanBuilder.and(QDocumentEntity.documentEntity.author.eq(author));
        }

        return findAll(booleanBuilder, pageable);
    }
//    List<DocumentEntity> findByCategoryAndCategoryId(CategoryDocument categoryDocument, Long categoryId);

}
