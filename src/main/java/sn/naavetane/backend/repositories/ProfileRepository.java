package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.ProfileEntity;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.naavetane.backend.entities.QProfileEntity;
import org.apache.commons.lang3.StringUtils;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>, QuerydslPredicateExecutor<ProfileEntity> {
    Optional<ProfileEntity> findByCode(String code);

    default Page<ProfileEntity> readAllByFilters(Pageable pageable, String code, String libelle) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(code)) {
            booleanBuilder.and(QProfileEntity.profileEntity.code.containsIgnoreCase(code));
        }

        if (StringUtils.isNotEmpty(libelle)) {
            booleanBuilder.and(QProfileEntity.profileEntity.libelle.containsIgnoreCase(libelle));
        }

        return findAll(booleanBuilder, pageable);
    }
}
