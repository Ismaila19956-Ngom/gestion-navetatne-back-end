package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.CadreLogiqueEntity;
import com.webgram.dgpsn.entities.QCadreLogiqueEntity;
import com.webgram.dgpsn.entities.enums.CadreLogiqueType;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface CadreLogiqueRepository extends JpaRepository<CadreLogiqueEntity, Long>, QuerydslPredicateExecutor<CadreLogiqueEntity> {
    Optional<CadreLogiqueEntity> findByLibelle(String libelle);

    default Page<CadreLogiqueEntity> readAllByFiltering(Pageable pageable,String code, String libelle, CadreLogiqueType codeType, Long regionId, Long departementId, Long arrondissementId) {
        var booleanBuider = new BooleanBuilder();
        if(Objects.nonNull(codeType)) {
            booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.typeCadreLogique.eq(codeType));
            if(codeType.equals(CadreLogiqueType.DEPARTEMENT)) {
                if(Objects.nonNull(regionId)){
                    booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.parent.id.eq(regionId));
                }

            }
            if(codeType.equals(CadreLogiqueType.ARRONDISSEMENT)) {
                if(Objects.nonNull(regionId)){
                    booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.parent.parent.id.eq(regionId));
                }
                if(Objects.nonNull(departementId)){
                    booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.parent.id.eq(departementId));
                }

            }
            if(codeType.equals(CadreLogiqueType.COMMUNE)) {
                if(Objects.nonNull(regionId)){
                    booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.parent.parent.parent.id.eq(regionId));
                }
                if(Objects.nonNull(departementId)){
                    booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.parent.parent.id.eq(departementId));
                }
                if(Objects.nonNull(arrondissementId)){
                    booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.parent.id.eq(arrondissementId));
                }

            }
        }
        if(StringUtils.isNotEmpty(code)) {
            booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)) {
            booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.libelle.containsIgnoreCase(libelle));
        }



        return findAll(booleanBuider, pageable);
    }

    default Page<CadreLogiqueEntity> findParent(Pageable pageable){
        var booleanBuider = new BooleanBuilder();
        booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.parent.isNull());
        return findAll(booleanBuider, pageable);
    };

    default Page<CadreLogiqueEntity> getByParent(Pageable pageable, Long parentId, Long projectId){
        var booleanBuider = new BooleanBuilder();
        booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.parent.id.eq(parentId));

       // booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.id.in(ids));

        return findAll(booleanBuider, pageable);
    };
    default Page<CadreLogiqueEntity> getRegions(Pageable pageable, CadreLogiqueType codeTypeCadrelogique){
        var booleanBuider = new BooleanBuilder();
        booleanBuider.and(QCadreLogiqueEntity.cadreLogiqueEntity.typeCadreLogique.eq(codeTypeCadrelogique));
        return findAll(booleanBuider, pageable);
    };

}
