package com.webgram.dgpsn.models.responses;

import lombok.*;
import com.webgram.dgpsn.entities.enums.TypeProjet;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagementUnitTreeDTO {
    private Long id;
    private String code;
    private String name;
    private TypeProjet type;
    private List<ManagementUnitTreeDTO> children = new ArrayList<>();
}