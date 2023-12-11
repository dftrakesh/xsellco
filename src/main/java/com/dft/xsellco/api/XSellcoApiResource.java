package com.dft.xsellco.api;

import com.dft.xsellco.XSellco;
import com.dft.xsellco.dto.InventoryReportDataRow;
import com.dft.xsellco.handler.JsonBodyHandler;
import com.dft.xsellco.report.InventoryReportRequest;
import com.dft.xsellco.report.ListReportsResponse;
import com.dft.xsellco.report.SubmittedReportResponse;
import com.dft.xsellco.report.UploadRepricerFileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XSellcoApiResource extends XSellco {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public XSellcoApiResource(String userName, String password) {
        super(userName, password);
    }

    @SneakyThrows
    public SubmittedReportResponse sendReportRequest(Date startDate, Date endDate) {
        InventoryReportRequest inventoryReportRequest = new InventoryReportRequest(startDate, endDate);
        String jsonBody = objectMapper.writeValueAsString(inventoryReportRequest);
        URI uri = baseUrl("reports/requests");
        HttpRequest request = post(uri, jsonBody);
        HttpResponse.BodyHandler<SubmittedReportResponse> handler = new JsonBodyHandler<>(SubmittedReportResponse.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public ListReportsResponse getReportsList() {
        URI uri = baseUrl("reports/requests");
        HttpRequest request = get(uri);
        HttpResponse.BodyHandler<ListReportsResponse> handler = new JsonBodyHandler<>(ListReportsResponse.class);
        return getRequestWrapped(request, handler);
    }

    @SneakyThrows
    public List<InventoryReportDataRow> getReportFileData(String reportID) {
        URI uri = baseUrl("reports/download/" + reportID);
        HttpRequest request = get(uri);
        List<InventoryReportDataRow> list = new ArrayList<>();
        File file = downloadReport(request);
        if (file != null) {
            Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            CsvToBean<InventoryReportDataRow> csvToBean = new CsvToBeanBuilder<InventoryReportDataRow>(reader)
                    .withSeparator(',')
                    .withType(InventoryReportDataRow.class)
                    .withIgnoreLeadingWhiteSpace(Boolean.TRUE)
                    .build();
            list = csvToBean.parse();
        }
        return list;
    }

    @SneakyThrows
    public UploadRepricerFileResponse uploadRepricerFile(File file) {
        URI uri = baseUrl("repricers");
        HttpRequest request = post(uri, file);
        return getRequestWrapped(request, new JsonBodyHandler<>(UploadRepricerFileResponse.class));
    }
}