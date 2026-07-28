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

public enum SexType {
    M("Masculin"),
    F("Féminin");


    @Getter
    @Setter
    private String description;

    SexType(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SexType fromValue(Object sexType) {
        if (sexType instanceof Map) {
            Map<String, Object> mapSexType = (Map<String, Object>) sexType;
            if (mapSexType.containsKey("name")) {
                return SexType.valueOf(mapSexType.get("name").toString());
            }
        }
        if (sexType instanceof String) {
            return SexType.valueOf(sexType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", SexType.class, sexType, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<SexType> getAllSexe() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
