package com.webgram.dgpsn.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataPoint<X, Y> {
    private X x;
    private Y y;
}