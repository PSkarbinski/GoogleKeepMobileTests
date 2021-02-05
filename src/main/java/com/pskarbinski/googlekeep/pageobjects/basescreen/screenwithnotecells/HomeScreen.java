package com.pskarbinski.googlekeep.pageobjects.basescreen.screenwithnotecells;

import com.pskarbinski.googlekeep.utils.Waits;
import com.pskarbinski.googlekeep.pageobjects.AccountPopup;
import com.pskarbinski.googlekeep.pageobjects.basescreen.NoteScreen;
import com.pskarbinski.googlekeep.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import static com.pskarbinski.googlekeep.utils.Waits.waitUntilElementClickable;
import static com.pskarbinski.googlekeep.utils.Waits.waitUntilElementVisible;

public class HomeScreen extends ScreenWithNoteCells {

    // Available only on iOS
    @iOSXCUITFindBy(accessibility = "Continue")
    MobileElement continueBtn;

    // Available only on Android
    @AndroidFindBy(id = "com.google.android.keep:id/skip_welcome")
    MobileElement getStartedBtn;

    @AndroidFindBy(id = "com.google.android.keep:id/og_apd_ring_view")
    @iOSXCUITFindBy(accessibility = "kOGLAccessibilityIdentifierAccountParticleDiscButton")
    MobileElement userIcon;

    @AndroidFindBy(id = "com.google.android.keep:id/new_note_button")
    @iOSXCUITFindBy(accessibility = "Create note")
    MobileElement addNoteBtn;

    public HomeScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public MobileElement getAddNoteBtn() {
        return Waits.waitUntilElementClickable(addNoteBtn);
    }

    public HomeScreen logInIfAccNotPresent(String login, String password) {
        boolean noAccounts = false;

        // For a scenario without any accounts logged in
        if (driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")) {
            try {
                Waits.waitUntilElementClickable(continueBtn, 4).click();
                noAccounts = true;
                Helpers.logIn(login, password);
            } catch (TimeoutException e) {
                // For other scenarios the button is not visible and doesn't need to be clicked
            }
        } else if (driver.getClass().getTypeName().equals("io.appium.java_client.android.AndroidDriver")) {
            try {
                // Using direct find by as the element is not part of Google Keep app and shouldn't be an element of this class
                Waits.waitUntilElementVisible(By.xpath("//android.widget.EditText[@resource-id='identifierId']"));
                noAccounts = true;
                Helpers.logIn(login, password);
            } catch (TimeoutException e) {
                // For other scenarios the button is not visible and doesn't need to be clicked
            }
        }

        if (!noAccounts) {
            try {
                tapUserIcon();
            } catch (TimeoutException e) {
                // For a scenario where the popup was already opened by logout
            }

            new AccountPopup(driver).logInIfAccNotPresent(login, password);
        }

        return this;
    }

    public HomeScreen logoutIfAccPresent(String login) {
        if (driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")) {
            try {
                Waits.waitUntilElementClickable(continueBtn);
                return this;
            } catch (TimeoutException e) {
                // For a scenario where no accounts are logged in
            }
        } else if (driver.getClass().getTypeName().equals("io.appium.java_client.android.AndroidDriver")) {
            try {
                // Using direct find by as the element is not part of Google Keep app and shouldn't be an element of this class
                Waits.waitUntilElementVisible(By.xpath("//android.widget.EditText[@resource-id='identifierId']"));
                return this;
            } catch (TimeoutException e) {
                // For other scenarios the button is not visible and doesn't need to be clicked
            }
        }

        try {
            tapUserIcon();
        } catch (TimeoutException e) {
            // For a scenario where the popup was already opened by logout
        }

        return new AccountPopup(driver).logoutIfAccPresent(login);
    }

    public HomeScreen tapGetStartedIfVisible() {
        if (driver.getClass().getTypeName().equals("io.appium.java_client.android.AndroidDriver")) {
            try {
                Waits.waitUntilElementClickable(getStartedBtn).click();
            } catch (TimeoutException e) {
                // For some scenarios the button is not visible and doesn't need to be clicked
            }
        }

        return this;
    }

    public AccountPopup tapUserIcon() {
        Waits.waitUntilElementClickable(userIcon).click();
        return new AccountPopup(driver);
    }

    public NoteScreen tapAddNote() {
        // Increased wait time because loading the home screen takes a while after logging in with a different account
        Waits.waitUntilElementClickable(addNoteBtn, 7).click();
        return new NoteScreen(driver);
    }

}
