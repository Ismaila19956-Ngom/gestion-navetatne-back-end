package com.webgram.dgpsn.models.requests;

import lombok.Data;

@Data
public class SortRequest {
    private String property; //property used to sort result
    private boolean ascending;
}
