package com.dft.xsellco;

import com.dft.xsellco.constants.XSellcoConstantCodes;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class XSellco implements XSellcoConstantCodes {

    private final HttpClient client;
    int MAX_ATTEMPTS = 50;
    int TIME_OUT_DURATION = 120000;

    public XSellco(String userName, String password) {
        client = HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password.toCharArray());
                    }
                })
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(60))
                .build();
    }

    @SneakyThrows
    protected URI baseUrl(String path) {
        return new URI(new StringBuilder()
                .append(API_BASE)
                .append(FORWARD_SLASH)
                .append(path)
                .toString());
    }

    @SneakyThrows
    protected HttpRequest get(URI uri) {
        return HttpRequest.newBuilder(uri)
                .header(HTTP_REQUEST_PROPERTY_ACCEPT, HTTP_REQUEST_ACCEPT_TYPE_JSON)
                .GET().build();
    }

    @SneakyThrows
    protected HttpRequest post(URI uri, final String jsonBody) {
        return HttpRequest.newBuilder(uri)
                .header(HTTP_REQUEST_PROPERTY_CONTENT_TYPE, HTTP_REQUEST_CONTENT_TYPE_JSON)
                .header(HTTP_REQUEST_PROPERTY_ACCEPT, HTTP_REQUEST_ACCEPT_TYPE_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
    }

    @SneakyThrows
    protected HttpRequest post(URI uri, final File file) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .header(HTTP_REQUEST_PROPERTY_CONTENT_TYPE, HTTP_REQUEST_CONTENT_TYPE_CSV)
                .header(HTTP_REQUEST_PROPERTY_ACCEPT, HTTP_REQUEST_ACCEPT_TYPE_JSON)
                .POST(HttpRequest.BodyPublishers.ofFile(file.toPath()))
                .build();
    }

    @SneakyThrows
    public <T> T getRequestWrapped(HttpRequest request, HttpResponse.BodyHandler<T> handler) {
        return client
                .sendAsync(request, handler)
                .thenComposeAsync(response -> tryResend(client, request, handler, response, 1))
                .get()
                .body();
    }

    @SneakyThrows
    public File downloadReport(HttpRequest request) {
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        if (response.statusCode() == 200) {
            InputStream inputStream = response.body();
            File file = File.createTempFile("repricer", ".csv");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            inputStream.transferTo(fileOutputStream);
            return file;
        }
        return null;
    }

    @SneakyThrows
    public <T> CompletableFuture<HttpResponse<T>> tryResend(HttpClient client,
                                                            HttpRequest request,
                                                            HttpResponse.BodyHandler<T> handler,
                                                            HttpResponse<T> resp, int count) {
        if (resp.statusCode() == 429 && count < MAX_ATTEMPTS) {
            Thread.sleep(TIME_OUT_DURATION);
            return client.sendAsync(request, handler)
                    .thenComposeAsync(response -> tryResend(client, request, handler, response, count + 1));
        }
        return CompletableFuture.completedFuture(resp);
    }
}