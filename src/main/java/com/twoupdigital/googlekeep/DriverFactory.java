package com.twoupdigital.googlekeep;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    private static String deviceOS;
    private static String OSVersion;
    private static String deviceName;
    private static String UDID;
    private static String appiumServerUrl;
    private static final String IOSBundleID = "com.google.Keep";
    private static final String andAppPackage = "com.google.android.keep";
    private static final String andAppActivity = ".activities.BrowseActivity";

    public static void setProperties(Properties properties) {
        deviceOS = System.getProperty("deviceOS");
        if (deviceOS == null) deviceOS = properties.getProperty("deviceOS");
        OSVersion = System.getProperty("OSVersion");
        if (OSVersion == null) OSVersion = properties.getProperty("OSVersion");
        deviceName = System.getProperty("deviceName");
        if (deviceName == null) deviceName = properties.getProperty("deviceName");
        UDID = System.getProperty("UDID");
        appiumServerUrl = System.getProperty("appiumServerUrl");
        if (appiumServerUrl == null) appiumServerUrl = properties.getProperty("appiumServerUrl");
    }

    public static AppiumDriver<MobileElement> getDriver() throws MalformedURLException {
        if (deviceOS.equalsIgnoreCase("Android")) {
            return getAndroidDriver();
        } else if (deviceOS.equalsIgnoreCase("iOS")) {
            return getIOSDriver();
        } else {
            throw new NullPointerException("Unrecognised device operating system. Please use \"Android\" or \"iOS\".");
        }
    }

    public static AndroidDriver<MobileElement> getAndroidDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, OSVersion);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability("appPackage", andAppPackage);
        capabilities.setCapability("appActivity", andAppActivity);

        return new AndroidDriver<>(new URL(appiumServerUrl), capabilities);
    }

    public static IOSDriver<MobileElement> getIOSDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, OSVersion);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability(MobileCapabilityType.UDID, UDID);
        capabilities.setCapability("bundleId", IOSBundleID);

        return new IOSDriver<>(new URL(appiumServerUrl), capabilities);
    }

}
