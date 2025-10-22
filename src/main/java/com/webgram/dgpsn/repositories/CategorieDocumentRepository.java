package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.CategorieDocumentEntity;
import com.webgram.dgpsn.entities.QCategorieDocumentEntity;
import com.webgram.dgpsn.entities.enums.CategoryDocument;

import java.util.List;
import java.util.Objects;

@Repository
public interface CategorieDocumentRepository extends JpaRepository<CategorieDocumentEntity, Long>, QuerydslPredicateExecutor<CategorieDocumentEntity> {

    default Page<CategorieDocumentEntity> readByFiltering(Pageable pageable, CategoryDocument categoryDocument) {
        var booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(categoryDocument)) {
            booleanBuilder.and(QCategorieDocumentEntity.categorieDocumentEntity.categoryDocument.eq(categoryDocument));
        }

        return findAll(booleanBuilder, pageable);
    }

   List<CategorieDocumentEntity> findByCategoryDocumentAndObligatoireTrue(CategoryDocument categoryDocument);


}
