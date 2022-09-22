import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.google.gson.JsonSyntaxException;
import models.Post;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiUtils;
import utils.StatusCode;

import java.net.http.HttpResponse;

public class ApiTest {

    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");
    private final Logger logger = Logger.getInstance();

    @Test
    public void apiTest() {
        Configurator.setRootLevel(Level.DEBUG);
        // STEP 1
        logger.info("Sending request to get all posts.");
        HttpResponse<String> allPostsResponse = ApiUtils.getAllPosts();
        Assert.assertEquals(StatusCode.getByValue(allPostsResponse.statusCode()), StatusCode.OK, "response status code is not 200");

        boolean isResponseBodyJson = true;
        Post[] posts = null;
        try {
            posts = Post.createArrayFromJson(allPostsResponse.body());
        } catch (JsonSyntaxException e) {
            isResponseBodyJson = false;
        }
        Assert.assertTrue(isResponseBodyJson, "response body is not a valid json string");

        boolean isOrderAscending = true;
        for (int i = 0; i < posts.length - 1; i++) {
            if (posts[i].compareTo(posts[i + 1]) > 0) {
                isOrderAscending = false;
                break;
            }
        }
        Assert.assertTrue(isOrderAscending, "posts in response body are not in ascending order");

        // STEP 2
        int correctPostId = Integer.parseInt(testData.getValue("/requestedPost/id").toString());
        logger.info("Sending request to get post with specified id of " + correctPostId);
        HttpResponse<String> postWithSpecificIdResponse = ApiUtils.getPostWithId(correctPostId);
        Assert.assertEquals(StatusCode.getByValue(postWithSpecificIdResponse.statusCode()), StatusCode.OK,
                "response status code is not 200");
        Post post = Post.createFromJson(postWithSpecificIdResponse.body());
        int correctUserId = Integer.parseInt(testData.getValue("/requestedPost/userId").toString());

        Assert.assertEquals(post.getUserId(), correctUserId, "user id of received post is not " + correctUserId);
        Assert.assertEquals(post.getId(), correctPostId, "id of received post is not " + correctPostId);
        Assert.assertFalse(StringUtils.isEmpty(post.getBody()), "post body should not be empty");
        Assert.assertFalse(StringUtils.isEmpty(post.getTitle()), "post title should not be empty");

        // STEP 3
        int incorrectPostId = Integer.parseInt(testData.getValue("/incorrectPost/id").toString());
        logger.info("Sending request to get post which do not exists, with id of " + incorrectPostId);
        HttpResponse<String> nonExistingPostResponse = ApiUtils.getPostWithId(incorrectPostId);
        Assert.assertEquals(StatusCode.getByValue(nonExistingPostResponse.statusCode()), StatusCode.NOT_FOUND,
                "response status code is not 404");

        Post nonExistingPost = Post.createFromJson(nonExistingPostResponse.body());
        Assert.assertTrue(StringUtils.isEmpty(nonExistingPost.getBody()), "response body should be empty");

        // STEP 4
        logger.info("Sending request to create post");
        Post randomPost = Post.createRandomPost();
        HttpResponse<String> createPostResponse = ApiUtils.sendPost(randomPost);
        Assert.assertEquals(StatusCode.getByValue(createPostResponse.statusCode()), StatusCode.CREATED,
                "response status code is not 201");

        Post createdPost = Post.createFromJson(createPostResponse.body());
        Assert.assertEquals(randomPost.getUserId(), createdPost.getUserId(), "posted userId does not match the one in confirmation response");
        Assert.assertEquals(randomPost.getBody(), createdPost.getBody(), "posted body does not match the one in confirmation response");
        Assert.assertEquals(randomPost.getTitle(), createdPost.getTitle(), "posted title does not match the one in confirmation response");
        Assert.assertNotNull(createdPost.getId(), "post id was not present in response");
    }
}
