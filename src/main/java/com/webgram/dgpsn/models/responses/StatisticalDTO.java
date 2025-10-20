package com.webgram.dgpsn.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticalDTO {
    private String label;
    private Long value;

    public static StatisticalDTO getStatical(String key, List<StatisticalDTO> stats) {
        StatisticalDTO result = null;
        for (StatisticalDTO stat : stats) {
            if(Objects.equals(key, stat.getLabel())) {
                result = stat;
                break;
            }
        }
        return result;
    }
}
