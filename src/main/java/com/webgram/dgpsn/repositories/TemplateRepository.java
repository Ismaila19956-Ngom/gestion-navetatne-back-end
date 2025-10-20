package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QTemplateEntity;
import com.webgram.dgpsn.entities.enums.CategorieAlerte;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.entities.enums.TypeAlerte;
import com.webgram.dgpsn.entities.TemplateEntity;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long>, QuerydslPredicateExecutor<TemplateEntity> {
    Optional<TemplateEntity> findByTypeAlerte(TypeAlerte typeAlerte);

    default Page<TemplateEntity> readAllByFilters(Pageable pageable, String libelle, CategorieAlerte categorieAlerte, TypeAlerte typeAlerte, Priority priority) {
        var booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QTemplateEntity.templateEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(categorieAlerte)){
            booleanBuilder.and(QTemplateEntity.templateEntity.categorieAlerte.eq(categorieAlerte));
        }
        if(Objects.nonNull(typeAlerte)){
            booleanBuilder.and(QTemplateEntity.templateEntity.typeAlerte.eq(typeAlerte));
        }

        if(Objects.nonNull(priority)){
            booleanBuilder.and(QTemplateEntity.templateEntity.priority.eq(priority));
        }
        return findAll(booleanBuilder, pageable);
    }
}
