import base.ApiTestBase;
import com.google.gson.JsonSyntaxException;
import models.Post;
import models.User;
import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiAccessPoints;
import utils.ComparatorUtils;
import utils.StatusCode;

import java.net.http.HttpResponse;
import java.util.Arrays;

public class ApiTest extends ApiTestBase {

    @Test
    public void apiTest() {
        logger.debug("step 1");

        HttpResponse<String> allPostsResponse = ApiAccessPoints.getAllPosts();
        Assert.assertEquals(allPostsResponse.statusCode(), StatusCode.OK.value, "response status code is not 200");

        Post[] posts = null;
        try {
            posts = gson.fromJson(allPostsResponse.body(), Post[].class);
        } catch (JsonSyntaxException e) {
            Assert.fail("response body is not a valid json string");
        }
        Assert.assertTrue(ComparatorUtils.getPostComparatorById().isOrdered(Arrays.asList(posts)), "posts in response body are not in ascending order");

        logger.debug("step 2");
        int specificPostId = Integer.parseInt(testData.getValue("/step2/requestedPost/id").toString());
        HttpResponse<String> postWithSpecificIdResponse = ApiAccessPoints.getPostWithId(specificPostId);
        Assert.assertEquals(postWithSpecificIdResponse.statusCode(), StatusCode.OK.value, "response status code is not 200");

        Post post = gson.fromJson(postWithSpecificIdResponse.body(), Post.class);
        int expectedUserId = Integer.parseInt(testData.getValue("/step2/requestedPost/userId").toString());

        Assert.assertEquals(post.userId, expectedUserId, "user id of received post is not " + expectedUserId);
        Assert.assertEquals(post.id, specificPostId, "id of received post is not " + specificPostId);
        Assert.assertFalse(StringUtils.isEmpty(post.body), "post body should not be empty");
        Assert.assertFalse(StringUtils.isEmpty(post.title), "post title should not be empty");

        logger.debug("step 3");
        int incorrectPostId = Integer.parseInt(testData.getValue("/step3/incorrectPost/id").toString());
        HttpResponse<String> nonExistingPostResponse = ApiAccessPoints.getPostWithId(incorrectPostId);
        Assert.assertEquals(nonExistingPostResponse.statusCode(), StatusCode.NOT_FOUND.value,
                "response status code is not 404");

        Post nonExistingPost = gson.fromJson(nonExistingPostResponse.body(), Post.class);
        Assert.assertTrue(StringUtils.isEmpty(nonExistingPost.body), "response body should be empty");

        logger.debug("step 4");
        Post randomPost = Post.createTestPost();
        HttpResponse<String> createPostResponse = ApiAccessPoints.sendPost(randomPost);
        Assert.assertEquals(createPostResponse.statusCode(), StatusCode.CREATED.value, "response status code is not 201");

        Post createdPost = gson.fromJson(createPostResponse.body(), Post.class);
        Assert.assertEquals(randomPost.userId, createdPost.userId, "posted userId does not match the one in confirmation response");
        Assert.assertEquals(randomPost.body, createdPost.body, "posted body does not match the one in confirmation response");
        Assert.assertEquals(randomPost.title, createdPost.title, "posted title does not match the one in confirmation response");
        Assert.assertNotNull(createdPost.id, "post id was not present in response");

        logger.debug("step 5");
        HttpResponse<String> allUsersResponse = ApiAccessPoints.getAllUsers();
        Assert.assertEquals(allUsersResponse.statusCode(), StatusCode.OK.value, "response status code is not 200");

        User[] users = null;
        try {
            users = gson.fromJson(allUsersResponse.body(), User[].class);
        } catch (Exception e) {
            Assert.fail("response body is not a valid json string");
        }

        User userFromResponse = null;
        int userFromTestDataId = Integer.parseInt(testData.getValue("/step5And6/sampleUser/id").toString());
        for (User user : users) {
            if (user.id == userFromTestDataId) {
                userFromResponse = user;
                break;
            }
        }
        User sampleUser = gson.fromJson(testData.getValue("/step5And6/sampleUser").toString(), User.class);
        Assert.assertEquals(userFromResponse, sampleUser);

        logger.debug("step 6");
        HttpResponse<String> userWithIdResponse = ApiAccessPoints.getUserWithId(userFromTestDataId);
        Assert.assertEquals(userWithIdResponse.statusCode(), StatusCode.OK.value, "response status code is not 200");

        User specificUserRequestedById = gson.fromJson(userWithIdResponse.body(), User.class);
        Assert.assertEquals(sampleUser, specificUserRequestedById);
    }
}
