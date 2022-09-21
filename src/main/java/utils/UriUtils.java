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

    public static URI getUriToGetAllPosts() {
        URI getAllPostsUri = null;

        try {
            getAllPostsUri = new URIBuilder(configData.getValue("/baseUri").toString())
                    .setPath(configData.getValue("/postPath").toString())
                    .build();
        } catch (URISyntaxException e) {
            logger.error("Error creating URI: " + e.getMessage());
        }
        System.out.println(getAllPostsUri);
        return getAllPostsUri;
    }
}
