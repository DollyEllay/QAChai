package utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.Post;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiUtils {

    private static final Logger logger = Logger.getInstance();
    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");

    public static HttpResponse<String> getAllPosts() {
        HttpResponse<String> response = null;
        HttpRequest getAllPostsRequest = HttpRequest.newBuilder()
                .uri(UriUtils.getPostsUri())
                .GET()
                .build();

        try {
            response = HttpClient.newHttpClient().send(getAllPostsRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error("error sending http request: " + e.getMessage());
        }

        return response;
    }

    public static HttpResponse<String> getPostWithId(int postId) {
        HttpResponse<String> response = null;
        HttpRequest getAllPostsRequest = HttpRequest.newBuilder()
                .uri(UriUtils.uriToGetPostWithId(postId))
                .GET()
                .build();

        try {
            response = HttpClient.newHttpClient().send(getAllPostsRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error("error sending http request: " + e.getMessage());
        }
        return response;
    }

    public static HttpResponse<String> sendPost(Post post) {
        HttpResponse<String> response = null;
        HttpRequest getAllPostsRequest = HttpRequest.newBuilder()
                .uri(UriUtils.getPostsUri())
                .POST(HttpRequest.BodyPublishers.ofString(post.toJson()))
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
