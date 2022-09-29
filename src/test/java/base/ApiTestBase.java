package base;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.google.gson.Gson;

public class ApiTestBase {
    public static final ISettingsFile testData = new JsonSettingsFile("TestData.json");
    public static final Gson gson = new Gson();
    public static final Logger logger = Logger.getInstance();
}
