package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.Post;
import org.apache.commons.lang3.RandomStringUtils;

public class TestDataUtils {
    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");

    public static Post createTestPost() {
        Post postFromTestData = new Post();
        postFromTestData.id = Integer.parseInt(testData.getValue("/step4/randomPost/userId").toString());
        postFromTestData.title = RandomStringUtils.randomAlphabetic(15);
        postFromTestData.body = RandomStringUtils.randomAlphabetic(50);

        return postFromTestData;
    }
}
