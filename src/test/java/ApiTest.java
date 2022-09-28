import base.ApiTestBase;
import com.google.common.collect.Ordering;
import com.google.gson.JsonSyntaxException;
import models.Post;
import models.User;
import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiUtils;
import utils.StatusCode;

import java.net.http.HttpResponse;
import java.util.Arrays;

public class ApiTest extends ApiTestBase {

    @Test
    public void apiTest() {
        logger.debug("step 1");

        HttpResponse<String> allPostsResponse = ApiUtils.getAllPosts();
        Assert.assertEquals(allPostsResponse.statusCode(), StatusCode.OK.value, "response status code is not 200");

        Post[] posts = null;
        try {
            posts = gson.fromJson(allPostsResponse.body(), Post[].class);
        } catch (JsonSyntaxException e) {
            Assert.fail("response body is not a valid json string");
        }
        Ordering<Post> byIdOrdering = new Ordering<Post>() {
            @Override
            public int compare(Post post1, Post post2) {
                return Integer.compare(post1.getId(), post2.getId());
            }
        };
        Assert.assertTrue(byIdOrdering.isOrdered(Arrays.asList(posts)), "posts in response body are not in ascending order");

        logger.debug("step 2");
        int specificPostId = Integer.parseInt(testData.getValue("/step2/requestedPost/id").toString());
        HttpResponse<String> postWithSpecificIdResponse = ApiUtils.getPostWithId(specificPostId);
        Assert.assertEquals(postWithSpecificIdResponse.statusCode(), StatusCode.OK.value, "response status code is not 200");

        Post post = gson.fromJson(postWithSpecificIdResponse.body(), Post.class);
        int expectedUserId = Integer.parseInt(testData.getValue("/step2/requestedPost/userId").toString());

        Assert.assertEquals(post.getUserId(), expectedUserId, "user id of received post is not " + expectedUserId);
        Assert.assertEquals(post.getId(), specificPostId, "id of received post is not " + specificPostId);
        Assert.assertFalse(StringUtils.isEmpty(post.getBody()), "post body should not be empty");
        Assert.assertFalse(StringUtils.isEmpty(post.getTitle()), "post title should not be empty");

        logger.debug("step 3");
        int incorrectPostId = Integer.parseInt(testData.getValue("/step3/incorrectPost/id").toString());
        HttpResponse<String> nonExistingPostResponse = ApiUtils.getPostWithId(incorrectPostId);
        Assert.assertEquals(nonExistingPostResponse.statusCode(), StatusCode.NOT_FOUND.value,
                "response status code is not 404");

        Post nonExistingPost = gson.fromJson(nonExistingPostResponse.body(), Post.class);
        Assert.assertTrue(StringUtils.isEmpty(nonExistingPost.getBody()), "response body should be empty");

        logger.debug("step 4");
        Post randomPost = Post.createTestPost();
        HttpResponse<String> createPostResponse = ApiUtils.sendPost(randomPost);
        Assert.assertEquals(createPostResponse.statusCode(), StatusCode.CREATED.value, "response status code is not 201");

        Post createdPost = gson.fromJson(createPostResponse.body(), Post.class);
        Assert.assertEquals(randomPost.getUserId(), createdPost.getUserId(), "posted userId does not match the one in confirmation response");
        Assert.assertEquals(randomPost.getBody(), createdPost.getBody(), "posted body does not match the one in confirmation response");
        Assert.assertEquals(randomPost.getTitle(), createdPost.getTitle(), "posted title does not match the one in confirmation response");
        Assert.assertNotNull(createdPost.getId(), "post id was not present in response");

        logger.debug("step 5");
        HttpResponse<String> allUsersResponse = ApiUtils.getAllUsers();
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
            if (user.getId() == userFromTestDataId) {
                userFromResponse = user;
                break;
            }
        }
        User sampleUser = gson.fromJson(testData.getValue("/step5And6/sampleUser").toString(), User.class);
        Assert.assertEquals(userFromResponse, sampleUser);

        logger.debug("step 6");
        HttpResponse<String> userWithIdResponse = ApiUtils.getUserWithId(userFromTestDataId);
        Assert.assertEquals(userWithIdResponse.statusCode(), StatusCode.OK.value, "response status code is not 200");

        User specificUserRequestedById = gson.fromJson(userWithIdResponse.body(), User.class);
        Assert.assertEquals(sampleUser, specificUserRequestedById);
    }
}
