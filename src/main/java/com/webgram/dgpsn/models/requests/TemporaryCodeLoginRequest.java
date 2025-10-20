package com.webgram.dgpsn.models.requests;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
 public class TemporaryCodeLoginRequest {
    @NotBlank
    private String code;
 }