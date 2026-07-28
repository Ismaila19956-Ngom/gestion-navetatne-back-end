package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum FundingTypeConfig {

    FINANCING_NEED("Besoin Financement"),
    MOBILISATION("Mobilisation"),
    EXECUTION("Execution");

    @Getter
    private final String description;

    FundingTypeConfig(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static FundingTypeConfig fromValue(Object fundingTypeConfig) {
        if (fundingTypeConfig instanceof Map) {
            Map<String, Object> mapFundingTypeConfig = (Map<String, Object>) fundingTypeConfig;
            if (mapFundingTypeConfig.containsKey("name")) {
                return FundingTypeConfig.valueOf(mapFundingTypeConfig.get("name").toString());
            }
        }
        if (fundingTypeConfig instanceof String) {
            return FundingTypeConfig.valueOf(fundingTypeConfig.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", FundingTypeConfig.class, fundingTypeConfig, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<FundingTypeConfig> getAllTypeFundingTypeConfig() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
