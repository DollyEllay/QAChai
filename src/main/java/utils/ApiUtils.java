package utils;

import models.Post;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiUtils {
    public static HttpResponse<String> getAllPosts() {
        return ApiConnectionUtils.getRequest(UriUtils.getPostsUri());
    }

    public static HttpResponse<String> getPostWithId(int postId) {
        return ApiConnectionUtils.getRequest(UriUtils.uriToGetPostWithId(postId));
    }

    public static HttpResponse<String> sendPost(Post post) {
        return ApiConnectionUtils.postRequest(UriUtils.getPostsUri(), HttpRequest.BodyPublishers.ofString(post.toJson()));
    }

    public static HttpResponse<String> getAllUsers() {
        return ApiConnectionUtils.getRequest(UriUtils.getUsersUri());
    }

    public static HttpResponse<String> getUserWithId(int userId) {
        return ApiConnectionUtils.getRequest(UriUtils.uriToGetUserWithId(userId));
    }
}