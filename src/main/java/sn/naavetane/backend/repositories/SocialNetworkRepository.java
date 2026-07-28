package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.SocialNetworkEntity;
import java.util.UUID;
import java.util.List;

@Repository
public interface SocialNetworkRepository extends JpaRepository<SocialNetworkEntity, UUID> {
    List<SocialNetworkEntity> findByActifTrue();
}
