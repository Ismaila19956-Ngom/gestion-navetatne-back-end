package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.CadreLogiqueType;
import com.webgram.dgpsn.entities.enums.TypeProjet;


import java.util.List;
import java.util.Objects;

@Repository
public interface GeographicalLocationRepository extends JpaRepository<GeographicalLocationEntity, Long>, QuerydslPredicateExecutor<GeographicalLocationEntity> {

    List<GeographicalLocationEntity> findByProjetId(Long id);

    default Page<GeographicalLocationEntity> readAllByFilters(Pageable pageable, String code , String libelle, CadreLogiqueType cadreLogiqueType, Long projetId) {
        var booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QGeographicalLocationEntity.geographicalLocationEntity.projet.id.eq(projetId));
        }
        if(Objects.nonNull(cadreLogiqueType)){
            booleanBuilder.and(QGeographicalLocationEntity.geographicalLocationEntity.cadreLogique.typeCadreLogique.eq(cadreLogiqueType));
        }
        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QGeographicalLocationEntity.geographicalLocationEntity.cadreLogique.libelle.containsIgnoreCase(libelle));
        }

        if(StringUtils.isNotEmpty(code)){
            booleanBuilder.and(QGeographicalLocationEntity.geographicalLocationEntity.cadreLogique.code.containsIgnoreCase(code));
        }
        return findAll(booleanBuilder, pageable);
    }
     public List<GeographicalLocationEntity> findByCadreLogiqueParentAndProjet(CadreLogiqueEntity cadre, ManagementUnitEntity projet);

    @Query("SELECT cad.libelle, proj.name " +
            "FROM GeographicalLocationEntity gp " +
            "JOIN gp.cadreLogique cad " +
            "JOIN gp.projet proj " +
            "WHERE gp.projet.type = :type")
    List<Object[]> findProjectsByGeographicalLocation(@Param("type") TypeProjet type);

    @Query("SELECT gl FROM GeographicalLocationEntity gl WHERE gl.projet.id = :projectId")
    List<GeographicalLocationEntity> findByProject(@Param("projectId") Long projectId);

    @Query("select DISTINCT(geo.projet.parent) " +
            "from GeographicalLocationEntity geo where geo.cadreLogique.id = :regionId ")
    List<ManagementUnitEntity> getProgrammeByRgion(@Param("regionId") Long structureId);

    @Query("select geo.projet " +
            "from GeographicalLocationEntity geo where geo.cadreLogique.id = :regionId and geo.projet.parent.id = :parentId ")
    List<ManagementUnitEntity> getProjectByRegionAndProgramme(@Param("regionId") Long structureId, @Param("parentId") Long parentId);


}
