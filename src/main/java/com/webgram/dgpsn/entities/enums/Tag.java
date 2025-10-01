package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.text.MessageFormat;
import java.util.Map;

public enum Tag {
    USER,
    ACTIF_PASSIF,
    PASSIF,
    ACTIF,
    DIFFERENCE,
    DATE_DETECTION,
    //tag relatif au solde tresorie
    TOTAL_ENTREE,
    TOTAL_SORTIE,
    MOIS,
    LIBELLE,
    //tag relatif au bilan
    MONTANT_ACTIF,
    MONTANT_PASSIF,
    DATE,

    // Tags relatifs àu compte de resultat
    RESULTAT_NET,
    RESULTAT_COMMISSION,
    TOTAL_NET,
    CHARGE_EXPLOITATION,
    CHARGE_BRUTE_EXPLOITATION,
    RESULTAT_EXPLOITATION,
    RESULTAT_NET_EXPLOITATION,
    // Tags relatifs à la validaton etape workflow
    AGENT,
    ETAPE,
    DATE_VALIDATION,
    // Tags relatifs à la liquidité
    LIQUIDITE,
    FINANCEMENT,
    // workflow negative alerte seuil > 80
    RESULT_VALUE,
    ALERT_LEVEL,
    RECOMMENDED_ACTION,
    // Tags relatifs aux risques
    RISQUE_STRATEGIQUE,
    RISQUE_FINANCIER,
    REGLEMENTATION,

    // Tags relatifs aux ratios prudentiels
    SOLVABILITE,
    LIQUIDITE_RATIO,
    ENDETTEMENT,

    // Tags généraux
    DESCRIPTION,
    DATE_CREATION,
    UTILISATEUR_RESPONSABLE,
    ENTITE_AFFECTEE,
    SEVERITE,

    // Tags spécifiques aux alertes
    ALERT_TYPE,
    RESOLUTION_STATUS,
    IMPACT_POTENTIEL,

    // Tags relatifs aux personnes ou agents
    AGENT_CONCERNE,
    CHEF_DE_SERVICE,
    EQUIPE_RESPONSABLE,

    // Tags relatifs aux périodes ou durées
    PERIODE_IMPACT,
    DUREE_RESOLUTION,

    // Tags relatifs aux documents ou informations supplémentaires
    DOCUMENTS_ASSOCIES,
    COMMENTAIRES;



    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Tag fromValue(Object tag) {
        if (tag instanceof Map) {
            Map<String, Object> mapTag = (Map<String, Object>) tag;
            if (mapTag.containsKey("name")) {
                return Tag.valueOf(mapTag.get("name").toString());
            }
        }
        if (tag instanceof String) {
            return Tag.valueOf(tag.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Tag.class, tag, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name()
        );
    }
}
