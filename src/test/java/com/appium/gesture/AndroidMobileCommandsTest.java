package com.appium.gesture;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AndroidMobileCommandsTest {

    private AndroidDriver driver;
    private WebDriverWait wait;
    private HashMap<String, String> args;

    @Before
    public void setUp() throws IOException {
        args = new HashMap<>();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ESPRESSO);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/ApiDemos-debug.apk");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait  = new WebDriverWait(driver, 20);
    }

    @Test
    public void setDateTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.DateWidgets1");
        driver.startActivity(activity);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("change the date"))).click();
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("android:id/datePicker")));
        args.put("year", "2020");
        args.put("monthOfYear", "10");
        args.put("dayOfMonth", "25");
        args.put("element", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: setDate", args);
        String text = wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.id("android:id/date_picker_header_date"))).getText();
        assertEquals("Sun, Oct 25", text);
    }

    @Test
    public void setTimeTest() {
        Activity activity = new Activity("io.appium.android.apis", ".view.DateWidgets2");
        driver.startActivity(activity);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("android.widget.TimePicker")));
        args.put("hours", "11");
        args.put("minutes", "0");
        args.put("element", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile: setTime", args);
        String text = wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.id("io.appium.android.apis:id/dateDisplay"))).getText();
        assertEquals("11:00", text);
    }
}
