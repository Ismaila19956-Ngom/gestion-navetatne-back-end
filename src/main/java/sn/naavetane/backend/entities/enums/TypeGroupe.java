package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum TypeGroupe {
    GROUPE1("I"),
    GROUPE2("II");


    @Getter
    @Setter
    private String description;

    TypeGroupe(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeGroupe fromValue(Object typeGroupe) {
        if (typeGroupe instanceof Map) {
            Map<String, Object> mapTypeUnite = (Map<String, Object>) typeGroupe;
            if (mapTypeUnite.containsKey("name")) {
                return TypeGroupe.valueOf(mapTypeUnite.get("name").toString());
            }
        }
        if (typeGroupe instanceof String) {
            return TypeGroupe.valueOf(typeGroupe.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", TypeGroupe.class, typeGroupe, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<TypeGroupe> getAllGroupe() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
