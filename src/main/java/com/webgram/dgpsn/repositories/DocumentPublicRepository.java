package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.DocumentPublicEntity;
import com.webgram.dgpsn.entities.QDocumentPublicEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Repository
public interface DocumentPublicRepository extends JpaRepository<DocumentPublicEntity, Long>, QuerydslPredicateExecutor<DocumentPublicEntity> {

    default Page<DocumentPublicEntity> readAllByFilters(
            Pageable pageable, String titre, String date, String authors,
            String themes, Long documentTypeId, Long folderId, Boolean publish)
    {
        var booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotEmpty(titre)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.titre.containsIgnoreCase(titre));
        }
        if(Objects.nonNull(date)){
            try {
                Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.date.eq(parseDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(Objects.nonNull(authors)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.authors.containsIgnoreCase(authors));
        }
        if(Objects.nonNull(themes)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.themes.in(themes));
        }
        if(Objects.nonNull(documentTypeId)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.documentType.id.eq(documentTypeId));
        }
        if(Objects.nonNull(folderId)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.folder.id.eq(folderId));
        }
        if(Objects.nonNull(publish)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.publish.eq(publish));
        }
        return findAll(booleanBuilder, pageable);
    }

    default Page<DocumentPublicEntity> filterPublishedDocuments(
            Pageable pageable, String titre, String date, String authors,
            String themes, String documentType)
    {
        var booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.publish.eq(true));

        if(StringUtils.isNotEmpty(titre)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.titre.containsIgnoreCase(titre));
        }
        if(Objects.nonNull(date)){
            try {
                Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.date.eq(parseDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(Objects.nonNull(authors)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.authors.containsIgnoreCase(authors));
        }
        if(Objects.nonNull(themes)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.themes.in(themes));
        }
        if(StringUtils.isNotEmpty(documentType)){
            booleanBuilder.and(QDocumentPublicEntity.documentPublicEntity.documentType.libelle.eq(documentType));
        }
        return findAll(booleanBuilder, pageable);
    }

    void deleteAllByFolderId(Long folderId);
}
