package utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class UriUtils {
    private static final ISettingsFile configData = new JsonSettingsFile("ConfigData.json");
    private static final Logger logger = Logger.getInstance();

    private static URI buildUri(String path) {
        URI resultUri = null;

        try {
            resultUri = new URIBuilder(configData.getValue("/baseUri").toString())
                    .setPath(path)
                    .build();
        } catch (URISyntaxException e) {
            logger.error("Error creating URI: " + e.getMessage());
        }
        return resultUri;
    }

    public static URI getPostsUri() {
        return buildUri(configData.getValue("/postPath").toString());
    }

    public static URI uriToGetPostWithId(int postId) {
        return buildUri(configData.getValue("/postPath").toString() + postId);
    }

    public static URI getUsersUri() {
        return buildUri(configData.getValue("/usersPath").toString());
    }

    public static URI uriToGetUserWithId(int userId) {
        return buildUri(configData.getValue("/usersPath").toString() + userId);
    }
}
