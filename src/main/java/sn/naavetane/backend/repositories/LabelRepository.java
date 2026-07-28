package sn.naavetane.backend.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.LabelEntity;
import sn.naavetane.backend.entities.QLabelEntity;
import sn.naavetane.backend.entities.enums.ReferentielType;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, Long>, QuerydslPredicateExecutor<LabelEntity> {
    
    Optional<LabelEntity> findByCode(String code);

    default Page<LabelEntity> readByFiltering(Pageable pageable, ReferentielType referentielType, String label) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        
        if (referentielType != null) {
            booleanBuilder.and(QLabelEntity.labelEntity.referentielType.eq(referentielType));
        }
        
        if (StringUtils.isNotEmpty(label)) {
            booleanBuilder.and(QLabelEntity.labelEntity.libelle.containsIgnoreCase(label));
        }
        
        return findAll(booleanBuilder, pageable);
    }
}
