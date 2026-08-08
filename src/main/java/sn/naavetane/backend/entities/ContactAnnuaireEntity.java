package sn.naavetane.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "contact_annuaire")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactAnnuaireEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String bureau;
    private String adresse;
    private String telephone;
    private String email;
    @Builder.Default
    private Boolean actif = true;
}
