package utils;

import aquality.selenium.core.logging.Logger;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiUtils {
    private static final Logger logger = Logger.getInstance();

    public static HttpResponse<String> getAllPosts() {
        HttpResponse<String> response = null;
        HttpRequest getAllPostsRequest = HttpRequest.newBuilder()
                .uri(UriUtils.getUriToGetAllPosts())
                .GET()
                .build();

        try {
            response = HttpClient.newHttpClient().send(getAllPostsRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error("error sending http request: " + e.getMessage());
        }

        return response;
    }
}
