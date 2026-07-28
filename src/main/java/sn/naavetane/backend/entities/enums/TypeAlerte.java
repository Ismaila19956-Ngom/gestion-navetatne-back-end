package sn.naavetane.backend.entities.enums;

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

    DEMANDE_GONGE("Nouvelle demande conge",CategorieAlerte.ACTES_GESTION, List.of(Tag.DUREE, Tag.AGENT,Tag.DATE_DEBUT,Tag.TYPE_CONGE)),
    VALIDATION_CONGE("Validation congé",CategorieAlerte.ACTES_GESTION, List.of(Tag.TYPE_CONGE, Tag.DUREE,Tag.PERSONNE_EN_CONGE,Tag.DATE_DEBUT,Tag.DATE_FIN)),
    CESSATION_SERVICE("Nouvelle Cessation de Service",CategorieAlerte.ACTES_GESTION, List.of(Tag.DATE_CESSATION,Tag.AGENT,Tag.NOMBRE_JOUR_CESSATION)),
    APPROCHE_FIN_CESSATION("Approche date fin de Cessation Service",CategorieAlerte.ACTES_GESTION, List.of(Tag.DATE_CESSATION,Tag.AGENT,Tag.PERSONNE_EN_CONGE,Tag.NOMBRE_JOUR_CESSATION,Tag.DATE_FIN)),
    FIN_CESSATION("Cessation Service Terminée",CategorieAlerte.ACTES_GESTION, List.of(Tag.DATE_CESSATION,Tag.AGENT,Tag.NOMBRE_JOUR_CESSATION,Tag.DATE_FIN)),
    NOUVEL_ORDRE_DE_MISSION("Nouvel ordre de mission", CategorieAlerte.ACTES_GESTION, List.of(Tag.LIBELLE,Tag.DATE_DEBUT,Tag.DATE_FIN,Tag.AGENT)),
    FIN_MISSION("Fin de la mission", CategorieAlerte.ACTES_GESTION,List.of(Tag.LIBELLE,Tag.DATE_DEBUT,Tag.DATE_FIN,Tag.AGENT)),
    VALIDATION_MISSION("Validation d'une mission",CategorieAlerte.ACTES_GESTION,List.of(Tag.LIBELLE,Tag.DATE_DEBUT,Tag.DATE_FIN,Tag.AGENT)),
    NOUVEAU_RECRUTEMENT("Nouveau Recrutement", CategorieAlerte.ACTES_GESTION, List.of(Tag.LIBELLE,Tag.DATE,Tag.TYPE_CONTRAT)),
    NOUVEAU_COURRIER("Nouveau Courrier", CategorieAlerte.GESTION_COURRIER, List.of(Tag.LIBELLE,Tag.DATE_RECEPTION,Tag.NUMERO_REFERENCE,Tag.NATURE_COURRIER,Tag.MODE_ENVOI)),
    NOUVELLE_ARCHIVAGE("Nouvelle Archivage", CategorieAlerte.GESTION_COURRIER, List.of(Tag.LIBELLE,Tag.DATE_RECEPTION,Tag.NUMERO_REFERENCE,Tag.NATURE_COURRIER,Tag.MODE_ENVOI)),
    NOUVEAU_BUDGET("Nouveau Budget ", CategorieAlerte.FINANCES_GESTIONS, List.of(Tag.LIBELLE,Tag.MONTANT_BUDGET,Tag.DATE)),
    UPDATE__BUDGET("Modifiication Budget ", CategorieAlerte.FINANCES_GESTIONS, List.of(Tag.LIBELLE,Tag.MONTANT_BUDGET,Tag.DATE)),
    DELETE_BUDGET("Suppression Budget ", CategorieAlerte.FINANCES_GESTIONS, List.of(Tag.LIBELLE,Tag.MONTANT_BUDGET,Tag.DATE)),
    NOUVELLE_LIGNE_BUDGET("Nouvelle lignes  Budgetaires ", CategorieAlerte.FINANCES_GESTIONS, List.of(Tag.LIBELLE,Tag.MONTANT_BUDGET,Tag.DATE,Tag.COMPTE)),
    UPDATE_LIGNE_BUDGET("Modification lignes  Budgetaires ", CategorieAlerte.FINANCES_GESTIONS, List.of(Tag.RUBRIQUE,Tag.MONTANT_LIGNE_BUDGET,Tag.DATE,Tag.COMPTE)),
    DELETE_LIGNE_BUDGET("Suppression lignes  Budgetaires ", CategorieAlerte.FINANCES_GESTIONS, List.of(Tag.LIBELLE,Tag.MONTANT_BUDGET,Tag.DATE,Tag.COMPTE));



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
