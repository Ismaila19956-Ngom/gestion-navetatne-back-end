package com.webgram.dgpsn.models;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoteurDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nomEntreprise;

    private String personneContact;

    private String fonctionContact;

    private String adresseSiege;

    private String adresseSite;

    private String telephone;

    private String email;

    private String bureauEtudes;
}