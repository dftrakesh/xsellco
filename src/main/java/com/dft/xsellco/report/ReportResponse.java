package com.dft.xsellco.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReportResponse {

    @JsonProperty("reportRequestId")
    private String reportRequestId;

    @JsonProperty("reportType")
    private String reportType;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("schedule")
    private Boolean schedule;

    @JsonProperty("submittedDate")
    private String submittedDate;

    @JsonProperty("reportProcessingStatus")
    private String reportProcessingStatus;

    @JsonProperty("generatedReportId")
    private String generatedReportId;

    @JsonProperty("startedProcessingDate")
    private String startedProcessingDate;

    @JsonProperty("completedDate")
    private String completedDate;
}