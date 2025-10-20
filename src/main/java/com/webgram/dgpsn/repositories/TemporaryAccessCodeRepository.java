package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.TemporaryAccessCodeEntity;
import com.webgram.dgpsn.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TemporaryAccessCodeRepository extends JpaRepository<TemporaryAccessCodeEntity, Long> {
    Optional<TemporaryAccessCodeEntity> findByCodeAndActiveTrueAndExpiryDateAfter(String code, LocalDateTime now);
    Optional<TemporaryAccessCodeEntity> findByUserAndActiveTrueAndExpiryDateAfter(UserEntity user, LocalDateTime now); // Pour éviter de créer plusieurs codes actifs pour un même user
    List<TemporaryAccessCodeEntity> findByUser_Login(String userName);
    Optional<TemporaryAccessCodeEntity> findByCode(String code);
}