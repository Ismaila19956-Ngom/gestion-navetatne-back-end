package com.webgram.dgpsn.models.responses.ptba;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PtbaTrimesterDTO {
    private Boolean janvier;
    private Boolean fevrier;
    private Boolean mars;
    private Boolean avril;
    private Boolean mai;
    private Boolean juin;
    private Boolean juillet;
    private Boolean aout;
    private Boolean septembre;
    private Boolean octobre;
    private Boolean novembre;
    private Boolean decembre;
}
