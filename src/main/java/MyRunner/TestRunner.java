package MyRunner;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@CucumberOptions(
        // Pointing to the actual location seen in your file tree
        features = "src/main/java/resources/features", 
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    public static RemoteWebDriver driver; // Changed to public static for easier access in Hook

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setDeviceName("Galaxy S21");
        options.setPlatformVersion("13");
        options.setApp("lt://APP10160171061769791489195489");

        Map<String, Object> ltOptions = new HashMap<>();
        // Ensure these environment variables are set on your machine
        ltOptions.put("user", System.getenv("LT_USERNAME"));
        ltOptions.put("accessKey", System.getenv("LT_ACCESS_KEY"));
        ltOptions.put("isRealMobile", true);
        ltOptions.put("build", "LT-Appium-Cucumber");
        ltOptions.put("name", "Proverbial App Scenario");
        ltOptions.put("video", true);
        ltOptions.put("visual", true);
        ltOptions.put("devicelog", true);

        options.setCapability("lt:options", ltOptions);

        driver = new RemoteWebDriver(
                new URL("https://mobile-hub.lambdatest.com/wd/hub"),
                options
        );
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}