package utils;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiAccessPoints {
    public static HttpResponse<String> getAllPosts() {
        return ApiUtils.getRequest(UriUtils.getPostsUri());
    }

    public static HttpResponse<String> getPostWithId(int postId) {
        return ApiUtils.getRequest(UriUtils.uriToGetPostWithId(postId));
    }

    public static HttpResponse<String> sendPostJson(String postJson) {
        return ApiUtils.postRequest(UriUtils.getPostsUri(), HttpRequest.BodyPublishers.ofString(postJson));
    }

    public static HttpResponse<String> getAllUsers() {
        return ApiUtils.getRequest(UriUtils.getUsersUri());
    }

    public static HttpResponse<String> getUserWithId(int userId) {
        return ApiUtils.getRequest(UriUtils.uriToGetUserWithId(userId));
    }
}