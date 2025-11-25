package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

public class FirstTestAndroidAppiumTestNG {
  private AndroidDriver driver;
  private WebDriverWait wait;

  // App details for SauceLabs MyDemo Android App
  private static final String APP_PACKAGE = "com.saucelabs.mydemoapp.android";
  private static final String APP_ACTIVITY = "com.saucelabs.mydemoapp.android.view.activities.MainActivity";
  private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";

  @BeforeMethod
  public void setUp() throws MalformedURLException {
    // Configure UiAutomator2 options for Android
    String appPath = Paths
        .get("build/mda-2.2.0-25.apk")
        .toAbsolutePath()
        .toString();

    UiAutomator2Options options = new UiAutomator2Options()
        .setPlatformName("Android")
        .setPlatformVersion("11")
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
  }

  @Test(priority = 1, description = "Verify app launches successfully")
  public void testAppLaunch() {
    System.out.println("Starting testAppLaunch...");

    WebElement appLogoAndName = wait.until(ExpectedConditions.visibilityOfElementLocated(
        AppiumBy.accessibilityId("App logo and name")));
    Assert.assertTrue(appLogoAndName.isDisplayed(), "App Logo should be visible");
    System.out.println("App launched successfully - App Logo is visible");
  }

  @AfterMethod
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