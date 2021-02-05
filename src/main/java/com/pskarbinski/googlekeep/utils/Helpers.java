package com.pskarbinski.googlekeep.utils;

import io.appium.java_client.MobileBy;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import java.util.Objects;

import static com.pskarbinski.googlekeep.utils.Waits.waitUntilElementClickable;
import static com.pskarbinski.googlekeep.utils.Waits.waitUntilElementVisible;

public class Helpers {

    public static void logIn(String login, String pw) {
        if (BaseHelper.driver.getClass().getTypeName().equals("io.appium.java_client.android.AndroidDriver")) {
            androidLogInToGoogleAccount(login, pw);
        } else if (BaseHelper.driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")) {
            IOSLogInToGoogleAccount(login, pw);
        }
    }

    public static void logOut(String login) {
        if (BaseHelper.driver.getClass().getTypeName().equals("io.appium.java_client.android.AndroidDriver")) {
            androidLogOutFromGoogleAccount(login);
        } else if (BaseHelper.driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")) {
            IOSLogOutFromGoogleAccount();
        }
    }

    public static void androidLogInToGoogleAccount(String login, String pw) {
        // Using try-catch and wider xpaths to support both old and new versions of Google login
        try {
            Waits.waitUntilElementClickable(By.xpath("//android.widget.Button[contains(@text,'S')]")).click();
        } catch (TimeoutException e) {
            // For some scenarios the button is not visible and doesn't need to be clicked
        }
        Waits.waitUntilElementClickable(By.xpath("//android.widget.EditText[@resource-id='identifierId']")).sendKeys(login);
        Waits.waitUntilElementClickable(By.xpath("//android.widget.Button[contains(@text,'N') or contains(@content-desc,'N')]")).click();
        Waits.waitUntilElementVisible(By.xpath("//android.widget.EditText[@password='true']")).sendKeys(pw + "\n");
        Waits.waitUntilElementClickable(By.xpath("//*[contains(@text,'N') or contains(@content-desc,'N') or contains(@resource-id,'N')]")).click();
        Waits.waitUntilElementClickable(By.xpath("//android.view.View[@resource-id='signinconsentNext']")).click();
        try {
            Waits.waitUntilElementClickable(By.xpath("//android.widget.Switch[@resource-id='com.google.android.gms:id/sud_items_switch']")).click();
        } catch (TimeoutException e) {
            // For some scenarios the button is not visible and doesn't need to be clicked
        }
        try {
            Waits.waitUntilElementClickable(By.xpath("//android.widget.Button[contains(@text,'M') or contains(@content-desc,'M')]")).click();
        }catch (TimeoutException e) {
            // For some scenarios the button is not visible and doesn't need to be clicked
        }
        try {
        Waits.waitUntilElementClickable(By.xpath("//android.widget.Button[contains(@text,'A')]")).click();
        } catch (TimeoutException e) {
            // For some scenarios the button is not visible and doesn't need to be clicked
        }
    }

    public static void IOSLogInToGoogleAccount(String login, String pw) {
        Waits.waitUntilElementClickable(MobileBy.AccessibilityId("Email or phone")).sendKeys(login);
        Waits.waitUntilElementClickable(MobileBy.AccessibilityId("Done")).click();
        Waits.waitUntilElementClickable(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();
        Waits.waitUntilElementClickable(MobileBy.AccessibilityId("Enter your password")).click();
        Waits.waitUntilElementClickable(MobileBy.AccessibilityId("Enter your password")).sendKeys(pw);
        Waits.waitUntilElementClickable(MobileBy.AccessibilityId("Done")).click();
        Waits.waitUntilElementClickable(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();
    }

    public static void androidLogOutFromGoogleAccount(String login) {
        // Using wider xpaths to support both old and new versions of Google login
        BaseHelper.driver.closeApp();
        Waits.waitUntilElementClickable(By.xpath("//android.widget.TextView[@text='" + login + "']")).click();
        Waits.waitUntilElementClickable(By.xpath("//android.widget.Button[contains(@text,'R')]")).click();
        Waits.waitUntilElementClickable(By.xpath("//android.widget.Button[contains(@text,'R')]")).click();
        BaseHelper.driver.launchApp();
    }

    public static void IOSLogOutFromGoogleAccount() {
        Waits.waitUntilElementVisible(MobileBy.AccessibilityId("ASMHeaderViewTitleLabel"));
        Objects.requireNonNull(MobileActions.swipeDownMaxTimesAndFindBy(1, MobileBy.AccessibilityId("kASMRemoveAccountButtonAccessibilityIdentifier"))).click();
        Waits.waitUntilElementClickable(MobileBy.AccessibilityId("kSSOAccessibilityIdentifierConfirmationRemoveButton")).click();
    }

    public static String generateRandomAlphanumericStringWithLengthBetween(int from, int to) {
        // 2nd argument is exclusive, adding 1 for the helper method to be more human friendly
        return RandomStringUtils.randomAlphanumeric(from, to + 1);
    }

}
