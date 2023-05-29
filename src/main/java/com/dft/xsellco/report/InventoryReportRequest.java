package com.dft.xsellco.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class InventoryReportRequest {

    @JsonProperty("reportType")
    private String reportType;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("channelName")
    private String channelName;

    public InventoryReportRequest(Date startDate, Date endDate) {
        this.reportType = "__INVENTORY__";
        this.startDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate) + "T00:00:00+00:0";
        this.endDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate) + "T23:59:59+00:0";
        this.channelName = "JBTools";
    }
}