package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

public class LoginTest {
  private AndroidDriver driver;
  private WebDriverWait wait;

  // App details for SauceLabs MyDemo Android App
  private static final String APP_PACKAGE = "com.saucelabs.mydemoapp.android";
  private static final String APP_ACTIVITY = "com.saucelabs.mydemoapp.android.view.activities.MainActivity";
  private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";

  @BeforeClass
  public void setUp() throws MalformedURLException {
    // Configure UiAutomator2 options for Android
    String appPath = Paths
        .get("build/mda-2.2.0-25.apk")
        .toAbsolutePath()
        .toString();

    UiAutomator2Options options = new UiAutomator2Options()
        .setPlatformName("Android")
        .setAutomationName("UiAutomator2")
        .setDeviceName("Android Emulator")
        .setApp(appPath)
        .setNoReset(false)
        .setAutoGrantPermissions(true)
        .setAppPackage(APP_PACKAGE)
        .setAppWaitActivity(APP_ACTIVITY);

    // Initialize AndroidDriver
    driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), options);
    // Initialize WebDriverWait
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    System.out.println("Test setup completed successfully");

    driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/menuIV")).click();
    driver.findElement(AppiumBy.accessibilityId("Login Menu Item")).click();
  }

  @Test()
  public void TC_01_LoginWithEmptyData(){
    driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/nameET")).sendKeys("");
    driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/passwordET")).sendKeys("");
    driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/loginBtn")).click();

    //verify
    Assert.assertEquals(driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/nameErrorTV")).getText(), "Username is required");
  }

  @Test()
  public void TC_02_LoginSuccess() {
    driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/nameET")).sendKeys("bob@example.com");
    driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/passwordET")).sendKeys("10203040");
    driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/loginBtn")).click();

    System.out.println("Login thành công!");
  }

  @Test()
  public void TC_03_Logout() throws InterruptedException {
    Thread.sleep(400);
    driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/menuIV")).click();
    driver.findElement(AppiumBy.accessibilityId("Logout Menu Item")).click();

    System.out.println("Logout thành công!");
  }

  @AfterClass
  public void tearDown() {
    if (driver != null) {
      try {
        System.out.println("Closing driver...");
        driver.quit();
        System.out.println("Driver closed successfully");
      } catch (Exception e) {
        System.err.println("Error closing driver: " + e.getMessage());
      }
    }
  }
}