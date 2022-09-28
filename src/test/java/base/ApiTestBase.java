package base;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.annotations.BeforeMethod;

public class ApiTestBase {
    public static final ISettingsFile testData = new JsonSettingsFile("TestData.json");
    public static final Gson gson = new Gson();
    public final Logger logger = Logger.getInstance();

    @BeforeMethod
    public static void testSetup() {
        Configurator.setRootLevel(Level.DEBUG);
    }
}
