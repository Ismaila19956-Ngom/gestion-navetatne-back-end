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
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.QStructureProjectEntity;
import com.webgram.dgpsn.entities.StructureProjectEntity;
import com.webgram.dgpsn.entities.enums.StructureProjectType;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.models.responses.StatisticalDTO;
import com.webgram.dgpsn.models.responses.StatisticalProjectDTO;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface StructureProjectRepository extends JpaRepository<StructureProjectEntity, Long>, QuerydslPredicateExecutor<StructureProjectEntity> {
    Optional<StructureProjectEntity> findByProjetAndStructureCode(ManagementUnitEntity project, String structureCode);

    List<StructureProjectEntity> findByProjetIdAndStructureProjectType(Long projectId, StructureProjectType structureProjectType);

    default Page<StructureProjectEntity> readAllByFiltering(Pageable pageable, Double montant, Long projetId, Long structureId, String startDate,
                                                            String endDate, String sortBy, Boolean ascending, TypeStructure typeStructure, StructureProjectType structureProjectType
            , List<StructureProjectType> structureProjectTypeList) throws ParseException {
        var booleanBuider = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if (Objects.nonNull(montant)) {
            booleanBuider.and(QStructureProjectEntity.structureProjectEntity.montant.eq(montant));
        }
        if (Objects.nonNull(projetId)) {
            booleanBuider.and(QStructureProjectEntity.structureProjectEntity.projet.id.eq(projetId));
        }
        if (Objects.nonNull(structureId)) {
            booleanBuider.and(QStructureProjectEntity.structureProjectEntity.structure.id.eq(structureId));
        }

        if (Objects.nonNull(typeStructure)) {
            booleanBuider.and(QStructureProjectEntity.structureProjectEntity.structure.typeStructure.eq(typeStructure));
        }

        if (Objects.nonNull(structureProjectType)) {
            booleanBuider.and(QStructureProjectEntity.structureProjectEntity.structureProjectType.eq(structureProjectType));
        }

        if (Objects.nonNull(structureProjectTypeList) && structureProjectTypeList.size() > 0) {
            booleanBuider.and(QStructureProjectEntity.structureProjectEntity.structureProjectType.in(structureProjectTypeList));
        }

        if (Objects.nonNull(startDate)) {
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            booleanBuider.and(QStructureProjectEntity.structureProjectEntity.startDate.eq(formatStartDate));
        }
//        if (Objects.nonNull(endDate)) {
//            var formatEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
//            booleanBuider.and(QStructureProjectEntity.structureProjectEntity.endDate.eq(formatEndDate));
//
//        }

        if (StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }

        if ((Objects.nonNull(ascending))) {
            sort.ascending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuider, pageRequest);
    }

//    @Query("select new sn.webg.suivievaluation.models.responses.ManagementUnitReportingDTO(sp.projet.parent.name, sp.projet) " +
//            "from StructureProjectEntity sp where sp.structure.id = :structureId group by sp.projet.parent.name ")
//    List<ManagementUnitReportingDTO> getReportingByStructrucure(@Param("structureId") Long structureId);

    @Query("select DISTINCT(sp.projet.parent) " +
            "from StructureProjectEntity sp where sp.structure.id = :structureId ")
    List<ManagementUnitEntity> getProgrammeByStructrucure(@Param("structureId") Long structureId);

    @Query("select sp.projet " +
            "from StructureProjectEntity sp where sp.structure.id = :structureId and sp.projet.parent.id = :parentId ")
    List<ManagementUnitEntity> getProjectByStructrucureAndProgramme(@Param("structureId") Long structureId, @Param("parentId") Long parentId);

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalProjectDTO(sp.structure.nom, COUNT(DISTINCT sp.projet)) " +
            "FROM StructureProjectEntity sp " +
            "JOIN sp.projet p " +
            "WHERE sp.structureProjectType = :structureProjectType " +
            "AND p.type = :typeProjet " +
            "GROUP BY sp.structure.nom")
    List<StatisticalProjectDTO> countDistinctProjectsByStructureProjectTypeAndTypeProjet(
            @Param("structureProjectType") StructureProjectType structureProjectType,
            @Param("typeProjet") TypeProjet typeProjet);

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalDTO(sp.structure.nom, COUNT(DISTINCT sp.projet)) " +
            "FROM StructureProjectEntity sp " +
            "JOIN sp.projet p " +
            "WHERE sp.structureProjectType = :structureProjectType " +
            "AND p.type = :typeProjet " +
            "GROUP BY sp.structure.nom")
    List<StatisticalDTO> countDistinctProjectsByStructure(
            @Param("structureProjectType") StructureProjectType structureProjectType,
            @Param("typeProjet") TypeProjet typeProjet);

}

