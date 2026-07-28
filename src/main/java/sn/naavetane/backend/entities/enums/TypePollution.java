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

public enum TypePollution {
    CHIMIQUE("pollution chimique"),
    DECHETS("pollution dechet dangereux"),
    PLASTIQUE("pollution plastique"),;

    @Getter
    @Setter
    private String description;

    TypePollution(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypePollution fromValue(Object priority) {
        if (priority instanceof Map) {
            Map<String, Object> mapPriority = (Map<String, Object>) priority;
            if (mapPriority.containsKey("name")) {
                return TypePollution.valueOf(mapPriority.get("name").toString());
            }
        }
        if (priority instanceof String) {
            return TypePollution.valueOf(priority.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", TypePollution.class, priority, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<TypePollution> getAllPriorities() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
