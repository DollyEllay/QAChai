package models;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.google.gson.Gson;
import org.apache.commons.lang.RandomStringUtils;

public class Post implements Comparable<Post> {
    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");
    private static final Gson gson = new Gson();

    private Integer userId;
    private Integer id;
    private String title;
    private String body;

    public static Post createRandomPost() {
        Post randomPost = new Post();
        randomPost.setUserId(Integer.parseInt(testData.getValue("/step4/randomPost/userId").toString()));
        randomPost.setTitle(RandomStringUtils.randomAlphabetic(15));
        randomPost.setBody(RandomStringUtils.randomAlphabetic(50));

        return randomPost;
    }

    public static Post createFromJson(String json) {
        return gson.fromJson(json, Post.class);
    }

    public static Post[] createArrayFromJson(String json) {
        return gson.fromJson(json, Post[].class);
    }

    public static boolean isOrderAscending(Post[] posts) {
        boolean isOrderAscending = true;
        for (int i = 0; i < posts.length - 1; i++) {
            if (posts[i].compareTo(posts[i + 1]) > 0) {
                isOrderAscending = false;
                break;
            }
        }
        return isOrderAscending;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int compareTo(Post otherPost) {
        return Integer.compare(getId(), otherPost.getId());
    }
}
