package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum TypeAlerte {

    DES_EQUILIBRE_ACTIF_PASSIF(
            "Déséquilibre actif/passif",
            CategorieAlerte.ACTIF_PASSIF,
            List.of(
                    Tag.USER,
                    Tag.ACTIF,
                    Tag.PASSIF,
                    Tag.DIFFERENCE,
                    Tag.SEVERITE,
                    Tag.DATE_DETECTION
            )
    ),
    IMPORT_SOLDE_TRESORERIE(
            "Nouvelle Importation Solde Tresorerie",
            CategorieAlerte.ACTIF_PASSIF,
            List.of(
                    Tag.USER,
                    Tag.TOTAL_ENTREE,
                    Tag.TOTAL_SORTIE,
                    Tag.LIBELLE,
                    Tag.MOIS
            )
    ),
    VALIDATION_SOLDE_TRESORERIE(
            "Validation Solde Tresorerie",
            CategorieAlerte.ACTIF_PASSIF,
            List.of(
                    Tag.USER,
                    Tag.TOTAL_ENTREE,
                    Tag.TOTAL_SORTIE,
                    Tag.LIBELLE,
                    Tag.MOIS
            )
    ),

    IMPORT_BILAN(
            "Nouvelle Importation Bilan",
            CategorieAlerte.ACTIF_PASSIF,
            List.of(
                    Tag.USER,
                    Tag.MONTANT_ACTIF,
                    Tag.MONTANT_PASSIF,
                    Tag.LIBELLE,
                    Tag.DATE
            )
    ),

    VALIDATION_BILAN(
            "Validation  Bilan",
            CategorieAlerte.ACTIF_PASSIF,
            List.of(
                    Tag.USER,
                    Tag.MONTANT_ACTIF,
                    Tag.MONTANT_PASSIF,
                    Tag.LIBELLE,
                    Tag.DATE
            )
    ),
    IMPORT_COMPTE_RESULTAT(
            "Nouvelle Importation compte resultat",
            CategorieAlerte.ACTIF_PASSIF,
            List.of(
                    Tag.USER,
                    Tag.RESULTAT_NET,
                    Tag.RESULTAT_EXPLOITATION,
                    Tag.LIBELLE,
                    Tag.TOTAL_NET,
                    Tag.CHARGE_EXPLOITATION,
                    Tag.CHARGE_BRUTE_EXPLOITATION,
                    Tag.RESULTAT_EXPLOITATION,
                    Tag.RESULTAT_NET_EXPLOITATION

            )
    ),

    VALIDATION_COMPTE_RESULTAT(
            "Validation  compte resultat",
            CategorieAlerte.ACTIF_PASSIF,
            List.of(
                    Tag.USER,
                    Tag.RESULTAT_NET,
                    Tag.RESULTAT_EXPLOITATION,
                    Tag.LIBELLE,
                    Tag.TOTAL_NET,
                    Tag.CHARGE_EXPLOITATION,
                    Tag.CHARGE_BRUTE_EXPLOITATION,
                    Tag.RESULTAT_EXPLOITATION,
                    Tag.RESULTAT_NET_EXPLOITATION
            )
    ),


    VALIDATION_ETAPE_WORKFLOW_LCR(
            "Validation  Etape Workflow LCR",
            CategorieAlerte.RESULTAT_INDICATEUR,
            List.of(
                    Tag.USER,
                    Tag.ETAPE,
                    Tag.DATE_VALIDATION
            )
    ),

    VALIDATION_ETAPE_WORKFLOW_NSFR(
            "Validation  Etape Workflow NSFR",
            CategorieAlerte.RESULTAT_INDICATEUR,
            List.of(
                    Tag.USER,
                    Tag.ETAPE,
                    Tag.DATE_VALIDATION
            )
    ),

    NEGATIVE_LCR_THRESHOLD(
    "Résultat LCR Inférieur au Seuil",
    CategorieAlerte.RESULTAT_INDICATEUR,
    List.of(
    Tag.USER,
    Tag.ETAPE,
    Tag.DATE_VALIDATION,
    Tag.RESULT_VALUE, // Valeur du résultat
    Tag.ALERT_LEVEL,  // Niveau d’alerte
    Tag.RECOMMENDED_ACTION // Action recommandée
    )
),
    NEGATIVE_NSFR_THRESHOLD(
    "Résultat NSFR Inférieur au Seuil",
    CategorieAlerte.RESULTAT_INDICATEUR,
    List.of(
    Tag.USER,
    Tag.ETAPE,
    Tag.DATE_VALIDATION,
    Tag.RESULT_VALUE,
    Tag.ALERT_LEVEL,
    Tag.RECOMMENDED_ACTION
    )
);


    @Getter
    @Setter
    String description;
    @Getter
    @Setter
    CategorieAlerte categorieAlerte;
    @Getter
    @Setter List<Tag> tags;

    TypeAlerte(String description, CategorieAlerte categorieAlerte, List<Tag> tags) {
        this.description = description;
        this.tags = tags;
        this.categorieAlerte = categorieAlerte;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeAlerte fromValue(Object typeAlerte) {
        if (typeAlerte instanceof Map) {
            Map<String, Object> mapTypeAlerte = (Map<String, Object>) typeAlerte;
            if (mapTypeAlerte.containsKey("name")) {
                return TypeAlerte.valueOf(mapTypeAlerte.get("name").toString());
            }
        }
        if (typeAlerte instanceof String) {
            return TypeAlerte.valueOf(typeAlerte.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", TypeAlerte.class, typeAlerte, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description,
                "categorieAlerte", categorieAlerte,
                "tags", tags
        );
    }

    public static Set<TypeAlerte> getAllTypesAlerte() {
        return Arrays.stream(values())
//                .filter(typeAlertes -> typeAlertes.categorieAlerte.equals(categorieAlerte))
                .collect(Collectors.toSet());
    }

    public static Set<TypeAlerte> getAllTypesAlerte(CategorieAlerte categorieAlerte) {
        return Arrays.stream(values())
                .filter(typeAlertes -> typeAlertes.categorieAlerte.equals(categorieAlerte))
                .collect(Collectors.toSet());
    }
}
