package com.dft.xsellco.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ListReportsResponse {

    @JsonProperty("meta")
    private List<Object> meta = null;

    @JsonProperty("code")
    private Long code;

    @JsonProperty("object")
    private String object;

    @JsonProperty("data")
    private List<ReportResponse> data;

    @JsonProperty("time")
    private Long time;
}