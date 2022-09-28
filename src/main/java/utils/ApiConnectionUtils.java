package utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiConnectionUtils {

    private static final Logger logger = Logger.getInstance();
    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");

    public static HttpResponse<String> getRequest(URI uri) {
        logger.info("Sending GET request to the uri: " + uri);
        HttpResponse<String> response = null;
        HttpRequest getAllPostsRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            response = HttpClient.newHttpClient().send(getAllPostsRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error("error sending http request: " + e.getMessage());
        }

        return response;
    }

    public static HttpResponse<String> postRequest(URI uri, HttpRequest.BodyPublisher body) {
        logger.info("Sending POST request to the uri: " + uri);
        HttpResponse<String> response = null;
        HttpRequest getAllPostsRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .header(testData.getValue("/headerName").toString(), testData.getValue("/headerValue").toString())
                .build();

        try {
            response = HttpClient.newHttpClient().send(getAllPostsRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error("error sending http request: " + e.getMessage());
        }
        return response;
    }
}
