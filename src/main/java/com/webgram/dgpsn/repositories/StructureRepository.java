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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QStructureEntity;
import com.webgram.dgpsn.entities.StructureEntity;
import com.webgram.dgpsn.entities.enums.TypeStructure;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StructureRepository extends JpaRepository<StructureEntity, Long>, QuerydslPredicateExecutor<StructureEntity> {
    Set<StructureEntity> findAllByIdIn(Set<Long> ids);
    Optional<StructureEntity> findByCode(String code);
    Optional<List<StructureEntity>> findByTypeStructure(TypeStructure typeStructure);
    default Page<StructureEntity> readAllByFiltering(Pageable pageable, String code, String nom
            , TypeStructure typeStructure, List<Long> idsToIgnore, List<TypeStructure> structureTypeList, String sortBy, Boolean ascending) {
        var booleanBuider = new BooleanBuilder();

        Sort sort = Sort.unsorted();

        if(StringUtils.isNotEmpty(code)) {
            booleanBuider.and(QStructureEntity.structureEntity.code.containsIgnoreCase(code));
        }

        if(StringUtils.isNotEmpty(nom)) {
            booleanBuider.and(QStructureEntity.structureEntity.nom.containsIgnoreCase(nom));
        }

        if(Objects.nonNull(typeStructure)) {
            booleanBuider.and(QStructureEntity.structureEntity.typeStructure.eq(typeStructure));
        }

        if(Objects.nonNull(idsToIgnore) && idsToIgnore.size() > 0) {
            booleanBuider.and(QStructureEntity.structureEntity.id.notIn(idsToIgnore));
        }
        if(Objects.nonNull(structureTypeList) && structureTypeList.size() > 0) {
            booleanBuider.and(QStructureEntity.structureEntity.typeStructure.in(structureTypeList));
        }

        if(StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }

        if((Objects.nonNull(ascending))) {
            sort.ascending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuider, pageRequest);
    }

    @Query("select count(s.id) from StructureEntity s where s.typeStructure = 'Partenaire'")
    Optional<Long> countTotalPartner();

    @Query("select s from StructureEntity s where s.id in(:ids)")
    Set<StructureEntity> findAllByIds(@Param("ids") List<Long> ids);
}
