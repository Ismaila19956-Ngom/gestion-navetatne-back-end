package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.AssignmentEntity;
import com.webgram.dgpsn.entities.QAssignmentEntity;
import com.webgram.dgpsn.entities.enums.StructureProjectType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long>, QuerydslPredicateExecutor<AssignmentEntity> {

    default Page<AssignmentEntity> readAllByFilters(
            Pageable pageable,
            String libelle,
            String startDate,
            String endDate,
            StructureProjectType structureProjectType,
            Long assignmentTypeId,
            Long structureId,
//            Long partenerId,
            Long projetId
    ) throws ParseException {
        var booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QAssignmentEntity.assignmentEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(startDate)){
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            booleanBuilder.and(QAssignmentEntity.assignmentEntity.startDate.eq(formatStartDate));
        }
        if(Objects.nonNull(endDate)){
            var formatEndDate= new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            booleanBuilder.and(QAssignmentEntity.assignmentEntity.endDate.eq(formatEndDate));
        }
        if(Objects.nonNull(assignmentTypeId)){
            booleanBuilder.and(QAssignmentEntity.assignmentEntity.assignmentType.id.eq(assignmentTypeId));
        }
        if(Objects.nonNull(structureId)){
            booleanBuilder.and(QAssignmentEntity.assignmentEntity.structure.id.eq(structureId));
        }
        if(Objects.nonNull(structureProjectType)){
            booleanBuilder.and(QAssignmentEntity.assignmentEntity.structureProjectType.eq(structureProjectType));
        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QAssignmentEntity.assignmentEntity.projet.id.eq(projetId));
        }

        return findAll(booleanBuilder, pageable);
    }
}
