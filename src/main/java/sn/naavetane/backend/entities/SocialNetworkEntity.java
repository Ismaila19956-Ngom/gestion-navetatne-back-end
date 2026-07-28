package sn.naavetane.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "social_networks")
@Data
public class SocialNetworkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String plateforme; // ex: Facebook, Instagram
    private String lien;
    private String icone; // ex: fa-facebook
    private Boolean actif = true;
}
