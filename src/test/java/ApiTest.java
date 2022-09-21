import aquality.selenium.core.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import models.Post;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiUtils;

import java.net.http.HttpResponse;

public class ApiTest {

    private static final Gson gson = new Gson();
    private final Logger logger = Logger.getInstance();

    @Test
    public void apiTest() {
        logger.info("Sending request to get all posts.");
        HttpResponse<String> response = ApiUtils.getAllPosts();
        Assert.assertEquals(response.statusCode(), 200, "response status code is not 200");

        boolean isResponseBodyJson = true;
        Post[] posts = null;
        try {
            posts = gson.fromJson(response.body(), Post[].class);
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
    }
}
