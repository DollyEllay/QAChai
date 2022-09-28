import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import models.Post;
import models.User;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ApiUtils;
import utils.StatusCode;

import java.net.http.HttpResponse;

public class ApiTest {

    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");
    private static final Gson gson = new Gson();
    private final Logger logger = Logger.getInstance();

    @BeforeMethod
    private static void testSetup() {
        Configurator.setRootLevel(Level.DEBUG);
    }

    @Test
    public void apiTest() {
        logger.debug("step 1");

        HttpResponse<String> allPostsResponse = ApiUtils.getAllPosts();
        Assert.assertEquals(StatusCode.getByValue(allPostsResponse.statusCode()), StatusCode.OK, "response status code is not 200");

        boolean isResponseBodyJson = true;
        Post[] posts = null;
        try {
            posts = gson.fromJson(allPostsResponse.body(), Post[].class);
        } catch (JsonSyntaxException e) {
            isResponseBodyJson = false;
        }
        Assert.assertTrue(isResponseBodyJson, "response body is not a valid json string");
        Assert.assertTrue(Post.isOrderAscending(posts), "posts in response body are not in ascending order");

        logger.debug("step 2");
        int specificPostId = Integer.parseInt(testData.getValue("/step2/requestedPost/id").toString());
        HttpResponse<String> postWithSpecificIdResponse = ApiUtils.getPostWithId(specificPostId);
        Assert.assertEquals(StatusCode.getByValue(postWithSpecificIdResponse.statusCode()), StatusCode.OK, "response status code is not 200");

        Post post = gson.fromJson(postWithSpecificIdResponse.body(), Post.class);
        int correctUserId = Integer.parseInt(testData.getValue("/step2/requestedPost/userId").toString());

        Assert.assertEquals(post.getUserId(), correctUserId, "user id of received post is not " + correctUserId);
        Assert.assertEquals(post.getId(), specificPostId, "id of received post is not " + specificPostId);
        Assert.assertFalse(StringUtils.isEmpty(post.getBody()), "post body should not be empty");
        Assert.assertFalse(StringUtils.isEmpty(post.getTitle()), "post title should not be empty");

        logger.debug("step 3");
        int incorrectPostId = Integer.parseInt(testData.getValue("/step3/incorrectPost/id").toString());
        HttpResponse<String> nonExistingPostResponse = ApiUtils.getPostWithId(incorrectPostId);
        Assert.assertEquals(StatusCode.getByValue(nonExistingPostResponse.statusCode()), StatusCode.NOT_FOUND,
                "response status code is not 404");

        Post nonExistingPost = gson.fromJson(nonExistingPostResponse.body(), Post.class);
        Assert.assertTrue(StringUtils.isEmpty(nonExistingPost.getBody()), "response body should be empty");

        logger.debug("step 4");
        Post randomPost = Post.createTestPost();
        HttpResponse<String> createPostResponse = ApiUtils.sendPost(randomPost);
        Assert.assertEquals(StatusCode.getByValue(createPostResponse.statusCode()), StatusCode.CREATED, "response status code is not 201");

        Post createdPost = gson.fromJson(createPostResponse.body(), Post.class);
        Assert.assertEquals(randomPost.getUserId(), createdPost.getUserId(), "posted userId does not match the one in confirmation response");
        Assert.assertEquals(randomPost.getBody(), createdPost.getBody(), "posted body does not match the one in confirmation response");
        Assert.assertEquals(randomPost.getTitle(), createdPost.getTitle(), "posted title does not match the one in confirmation response");
        Assert.assertNotNull(createdPost.getId(), "post id was not present in response");

        logger.debug("step 5");
        HttpResponse<String> allUsersResponse = ApiUtils.getAllUsers();
        Assert.assertEquals(StatusCode.getByValue(allUsersResponse.statusCode()), StatusCode.OK, "response status code is not 200");

        boolean isAllUsersResponseBodyJson = true;
        User[] users = null;
        try {
            users = gson.fromJson(allUsersResponse.body(), User[].class);
        } catch (Exception e) {
            isAllUsersResponseBodyJson = false;
        }
        Assert.assertTrue(isAllUsersResponseBodyJson, "response body is not a valid json string");

        User userFromResponse = null;
        int userFromTestDataId = Integer.parseInt(testData.getValue("/step5And6/sampleUser/id").toString());
        for (User user : users) {
            if (user.getId() == userFromTestDataId) {
                userFromResponse = user;
                break;
            }
        }
        User sampleUser = gson.fromJson(testData.getValue("/step5And6/sampleUser").toString(), User.class);
        Assert.assertEquals(userFromResponse, sampleUser);

        logger.debug("step 6");
        HttpResponse<String> userWithIdResponse = ApiUtils.getUserWithId(userFromTestDataId);
        Assert.assertEquals(StatusCode.getByValue(userWithIdResponse.statusCode()), StatusCode.OK, "response status code is not 200");

        User specificUserRequestedById = gson.fromJson(userWithIdResponse.body(), User.class);
        Assert.assertEquals(sampleUser, specificUserRequestedById);
    }
}
