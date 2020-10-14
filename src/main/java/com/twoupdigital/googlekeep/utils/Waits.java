package com.twoupdigital.googlekeep.utils;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;

public class Waits extends BaseHelper {

    private static long defaultTimeoutInSec;
    private static WebDriverWait wait;

    public static void setProperties(Properties properties) {
        if (System.getProperty("defaultTimeoutInSec") != null){
            defaultTimeoutInSec = Long.parseLong(System.getProperty("defaultTimeoutInSec"));
        } else {
            defaultTimeoutInSec = Long.parseLong(properties.getProperty("defaultTimeoutInSec"));
        }
    }

    public static WebDriverWait getNewStandardWait() {
        return new WebDriverWait(driver, defaultTimeoutInSec);
    }

    public static WebDriverWait getNonStandardWait(long timeoutInSec) {
        return new WebDriverWait(driver, timeoutInSec);
    }

    public static MobileElement waitUntilElementClickable(WebElement element) {
        wait = getNewStandardWait();
        return (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static MobileElement waitUntilElementClickable(By locator) {
        wait = getNewStandardWait();
        return (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static MobileElement waitUntilElementVisible(WebElement element) {
        wait = getNewStandardWait();
        return (MobileElement) wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static MobileElement waitUntilElementVisible(By locator) {
        wait = getNewStandardWait();
        return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @SuppressWarnings("unchecked")
    public static List<MobileElement> waitUntilAllElementsVisible(List<MobileElement> elements) {
        if (!elements.isEmpty()) {
            wait = getNewStandardWait();
            return (List<MobileElement>) (List<?>) wait.until(ExpectedConditions.visibilityOfAllElements((List<WebElement>) (List<?>) elements));
        } else {
            return null;
        }
    }

    public static MobileElement waitUntilElementClickable(WebElement element, long timeoutInSec) {
        wait = getNonStandardWait(timeoutInSec);
        return (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static MobileElement waitUntilElementClickable(By locator, long timeoutInSec) {
        wait = getNonStandardWait(timeoutInSec);
        return (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static MobileElement waitUntilElementVisible(WebElement element, long timeoutInSec) {
        wait = getNonStandardWait(timeoutInSec);
        return (MobileElement) wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static MobileElement waitUntilElementVisible(By locator, long timeoutInSec) {
        wait = getNonStandardWait(timeoutInSec);
        return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}
