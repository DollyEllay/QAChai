package models;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.google.gson.Gson;
import org.apache.commons.lang.RandomStringUtils;

public class Post {
    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");
    private static final Gson gson = new Gson();

    public Integer userId;
    public Integer id;
    public String title;
    public String body;

    public static Post createTestPost() {
        Post randomPost = new Post();
        randomPost.id = Integer.parseInt(testData.getValue("/step4/randomPost/userId").toString());
        randomPost.title = RandomStringUtils.randomAlphabetic(15);
        randomPost.body = RandomStringUtils.randomAlphabetic(50);

        return randomPost;
    }

    public String toJson() {
        return gson.toJson(this);
    }
}
