









package com.webgram.dgpsn.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.webgram.dgpsn.entities.AgentEntity;
import lombok.*;
import lombok.experimental.Accessors;


import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StaticCessationDTO {
    private AgentEntity agent;
    private String corpsLibelle;
    private Date dateCessation;
    private Integer nombreCessationEncours;



}





