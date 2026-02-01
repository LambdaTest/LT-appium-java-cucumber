package stepDefinitions;

import org.openqa.selenium.remote.RemoteWebDriver;
import io.cucumber.java.*;
import MyRunner.TestRunner;

public class Hook extends TestRunner {

    @Before
    public void updateName(Scenario scenario) {
        // Only logging that the app launched
        if (driver != null) {
            System.out.println("App launched successfully for: " + scenario.getName());
        }
    }

    @After
    public void close_the_browser(Scenario scenario) {
        if (driver != null) {
            System.out.println("Closing session ID: " + driver.getSessionId());
            // We keep driver.quit() here to ensure the session ends on LambdaTest
            driver.quit(); 
        }
    }
}