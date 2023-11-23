package MyRunner;

import java.net.URL;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import org.openqa.selenium.JavascriptExecutor;

import java.net.MalformedURLException;

@CucumberOptions(features = "src/main/java/Features/todo.feature", glue = {
  "stepDefinitions" }, plugin = "json:target/cucumber-reports/CucumberTestReport.json")

public class TestRunner extends AbstractTestNGCucumberTests {

  private TestNGCucumberRunner testNGCucumberRunner;

  public static RemoteWebDriver connection;

  @BeforeClass(alwaysRun = true)
  public void setUpCucumber() {
    testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
  }

  @BeforeMethod(alwaysRun = true)
  @Parameters({ "deviceName", "platformVersion", "platformName" })
  public void setUpClass(String deviceName, String platformVersion, String platformName) throws Exception {

    String username = System.getenv("LT_USERNAME") == null ?
      "YOUR_LT_USERNAME" :
      System.getenv("LT_USERNAME");      //Enter your LambdaTest username at the place of YOUR_LT_USERNAME
    String accesskey = System.getenv("LT_ACCESS_KEY") == null ?
      "YOUR_LT_ACCESSKEY" :
      System.getenv("LT_ACCESS_KEY");     //Enter your LambdaTest accessKey at the place of YOUR_LT_ACCESSKEY
    String app_id = System.getenv("LT_APP_ID") == null ?
      "lt://proverbial-android" :
      System.getenv("LT_APP_ID");      //Enter your LambdaTest username at the place of YOUR_LT_USERNAME
    String grid_url = System.getenv("LT_GRID_URL") == null ?
      "mobile-hub.lambdatest.com" :
      System.getenv("LT_GRID_URL");     //Enter your LambdaTest accessKey at the place of YOUR_LT_ACCESSKEY

    DesiredCapabilities capability = new DesiredCapabilities();

    capability.setCapability("platformName", platformName);
    capability.setCapability("deviceName", deviceName);
    capability.setCapability("platformVersion", platformVersion);

    capability.setCapability("build", "LT-appium-java-cucumber");
    capability.setCapability("test", "Test Parallel");
    capability.setCapability("isRealMobile", true);

    capability.setCapability("app", app_id);     //Enter the app url here
    capability.setCapability("devicelog", true);
    capability.setCapability("network", false);
    capability.setCapability("video", true);
    capability.setCapability("console", true);
    capability.setCapability("visual", true);

    String gridURL = "https://" + username + ":" + accesskey + "@" + grid_url + "/wd/hub";
    connection = new RemoteWebDriver(new URL(gridURL), capability);
  }

  @DataProvider
  public Object[][] features() {
    return testNGCucumberRunner.provideScenarios();
  }

  @AfterClass(alwaysRun = true)
  public void tearDownClass() {
    testNGCucumberRunner.finish();
  }
}
