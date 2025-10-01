package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.ProfileEntity;
import com.webgram.dgpsn.entities.QProfileEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>, QuerydslPredicateExecutor<ProfileEntity> {
    Optional<ProfileEntity> findByCode(String code);

    default Page<ProfileEntity> readAllByFilters(Pageable pageable, String code, String libelle) {
        var booleanBuilder = new BooleanBuilder();
        if(StringUtils.isNotEmpty(code)){
            booleanBuilder.and(QProfileEntity.profileEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QProfileEntity.profileEntity.libelle.containsIgnoreCase(libelle));
        }
        return findAll(booleanBuilder, pageable);
    }

    List<ProfileEntity> findAllById(Long id);
}
