    package com.webgram.dgpsn.entities;

    import com.webgram.dgpsn.entities.enums.StatutFournisseur;
    import com.webgram.dgpsn.entities.enums.TypeFournisseur;
    import com.webgram.dgpsn.entities.enums.TypePlanComptable;
    import lombok.*;
    import com.webgram.dgpsn.entities.audits.Auditable;
    import jakarta.persistence.*;
    import java.io.Serializable;

    @Table(name = "fournisseur") @Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public class FournisseurEntity extends Auditable<Long> implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "frn_id")
        private Long id;

        @Column(name = "frn_code", length = 50, unique = true)
        private String codeFournisseur;

        @Column(name = "frn_raison_sociale", length = 200)
        private String raisonSociale;

        @Column(name = "frn_ninea", length = 50)
        private String ninea;

        @Column(name = "frn_registre_commerce", length = 50)
        private String registreCommerce; // Facultatif

        @Column(name = "frn_type", length = 50)
        private TypeFournisseur typeFournisseur;
        @Column(name = "frn_telephone", length = 20)
        private String telephone;

        @Column(name = "frn_email", length = 100)
        private String email;

        @Column(name = "frn_adresse", length = 500)
        private String adresse;

        @Column(name = "frn_statut", length = 20)
        private StatutFournisseur statut;

        @Column(name = "frn_banque", length = 100)
        private String banque; // Facultatif

        @Column(name = "frn_rib", length = 50)
        private String rib; // Facultatif

        @Column(name = "frn_categorie", length = 50)
        private String categorieFournisseur;

        @Column(name = "frn_delai_paiement")
        private Integer delaiPaiement;

        @Column(name = "frn_nom_contact", length = 100)
        private String nomContact;

        @Column(name = "frn_fonction_contact", length = 100)
        private String fonctionContact;

        @Column(name = "frn_notes", length = 1000)
        private String notes;
    }