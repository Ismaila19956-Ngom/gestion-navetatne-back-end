package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum StructureProjectType {

    EXECUTION("Exécution"),
    GUARDIANSHIP("Tutelle"),
    PARTNER("Partenaire");

    @Getter
    private final String description;

    StructureProjectType(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StructureProjectType fromValue(Object statusType) {
        if (statusType instanceof Map) {
            Map<String, Object> mapStatusType = (Map<String, Object>) statusType;
            if (mapStatusType.containsKey("name")) {
                return StructureProjectType.valueOf(mapStatusType.get("name").toString());
            }
        }
        if (statusType instanceof String) {
            return StructureProjectType.valueOf(statusType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", StructureProjectType.class, statusType, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }
}
