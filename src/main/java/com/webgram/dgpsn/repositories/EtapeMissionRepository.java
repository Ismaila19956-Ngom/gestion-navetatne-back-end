package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.EtapeMissionEntity;
import com.webgram.dgpsn.entities.QEtapeMissionEntity;

import java.util.Date;
import java.util.Objects;

@Repository
public interface EtapeMissionRepository extends JpaRepository<EtapeMissionEntity, Long>, QuerydslPredicateExecutor<EtapeMissionEntity> {
    default Page<EtapeMissionEntity> readAllByFilters(
            Pageable pageable,
            String libelle,
            Date plannedStartDate,
            Date planedEndDate,
            Date actualStartDate,
            Date actualEndDate,
            Long statusId,
            Long responsableId,
            Long assignmentId

    ) {
        var booleanBuider = new BooleanBuilder();

        if (Objects.nonNull(libelle)) {
            booleanBuider.and(QEtapeMissionEntity.etapeMissionEntity.libelle.containsIgnoreCase(libelle));
        }
        if (Objects.nonNull(plannedStartDate)) {
            booleanBuider.and(QEtapeMissionEntity.etapeMissionEntity.plannedStartDate.eq(plannedStartDate));
        }
        if (Objects.nonNull(planedEndDate)) {
            booleanBuider.and(QEtapeMissionEntity.etapeMissionEntity.planedEndDate.eq(planedEndDate));
        }
        if (Objects.nonNull(actualStartDate)) {
            booleanBuider.and(QEtapeMissionEntity.etapeMissionEntity.actualStartDate.eq(actualStartDate));
        }
        if (Objects.nonNull(actualEndDate)) {
            booleanBuider.and(QEtapeMissionEntity.etapeMissionEntity.actualEndDate.eq(actualEndDate));
        }
            if (Objects.nonNull(statusId)) {
                booleanBuider.and(QEtapeMissionEntity.etapeMissionEntity.status.id.eq(statusId));
            }
            if (Objects.nonNull(responsableId)) {
                booleanBuider.and(QEtapeMissionEntity.etapeMissionEntity.responsable.id.eq(responsableId));
            }
        if (Objects.nonNull(assignmentId)) {
            booleanBuider.and(QEtapeMissionEntity.etapeMissionEntity.assignment.id.eq(assignmentId));
        }

            return findAll(booleanBuider, pageable);
        }

}
