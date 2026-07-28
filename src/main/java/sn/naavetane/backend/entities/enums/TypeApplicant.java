package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum TypeApplicant {

    ACTOR("Acteur"),
    STRUCTURE_TUTELLE("Structure tutelle"),
    STRUCTURE_EXECUTION("Structure d'éxecution"),
    PARTENAIRES("Partenaires"),
    BAILLEURS("Bailleur");

    @Getter
    private final String description;

    TypeApplicant(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeApplicant fromValue(Object typeApplicant) {
        if (typeApplicant instanceof Map) {
            Map<String, Object> mapTypeApplicant= (Map<String, Object>) typeApplicant;
            if (mapTypeApplicant.containsKey("name")) {
                return TypeApplicant.valueOf(mapTypeApplicant.get("name").toString());
            }
        }
        if (typeApplicant instanceof String) {
            return TypeApplicant.valueOf(typeApplicant.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", TypeApplicant.class, typeApplicant, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<TypeApplicant> getAllTypeFundingTypeConfig() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
