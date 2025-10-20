package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.QUgpProjetEntity;
import com.webgram.dgpsn.entities.UgpProjetEntity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public interface UgpProjetRepository extends JpaRepository<UgpProjetEntity, Long>, QuerydslPredicateExecutor<UgpProjetEntity> {

    List<UgpProjetEntity> findByProjetId(Long id);

    default Page<UgpProjetEntity> readAllByFiltering(Pageable pageable, Long projectId, Boolean existed, Boolean occupied, String status, Long ugpRoleId) {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(projectId)) {
            booleanBuider.and(QUgpProjetEntity.ugpProjetEntity.projet.id.eq(projectId));
        }
        if(Objects.nonNull(ugpRoleId)) {
            booleanBuider.and(QUgpProjetEntity.ugpProjetEntity.ugpRole.id.eq(ugpRoleId));
        }
        if(Objects.nonNull(existed)) {
            booleanBuider.and(QUgpProjetEntity.ugpProjetEntity.existed.eq(existed));
        }
        if(Objects.nonNull(occupied)) {
            booleanBuider.and(QUgpProjetEntity.ugpProjetEntity.occupied.eq(existed));
        }
        if(StringUtils.isNotEmpty(status)) {
            booleanBuider.and(QUgpProjetEntity.ugpProjetEntity.status.containsIgnoreCase(status));
        }

        return findAll(booleanBuider, pageable);
    }
    Set<UgpProjetEntity> findByProjet(ManagementUnitEntity projet);
}
