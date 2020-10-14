package com.twoupdigital.googlekeep.pageobjects;

import com.twoupdigital.googlekeep.pageobjects.basescreen.screenwithnotecells.HomeScreen;
import com.twoupdigital.googlekeep.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.twoupdigital.googlekeep.utils.Finders.findElementContaining;
import static com.twoupdigital.googlekeep.utils.Waits.*;

public class AccountPopup {

    protected AppiumDriver<MobileElement> driver;

    // Available only on iOS
    @iOSXCUITFindBy(accessibility = "Continue")
    MobileElement continueBtn;

    @AndroidFindBy(accessibility = "Close")
    @iOSXCUITFindBy(accessibility = "kOGLAccessibilityIdentifierCancelButton")
    MobileElement xButton;

    @AndroidFindBy(id = "com.google.android.keep:id/account_name")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCell[contains(@label,'Signed ') or contains(@label,'Use ')]")
    List<MobileElement> loggedInAccounts;

    @iOSXCUITFindBy(accessibility = "kOGLAccessibilityIdentifierSignInManageButton")
    MobileElement currentAcc;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Manage accounts on this device']")
    MobileElement manageAccountsBtn;

    @iOSXCUITFindBy(accessibility = "kOGLAccessibilityIdentifierMyAccountButton")
    MobileElement googleAccountBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add another account']")
    @iOSXCUITFindBy(accessibility = "kOGLAccessibilityIdentifierSignInAddAccountButton")
    MobileElement addAccBtn;

    public AccountPopup(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public HomeScreen closeAccPopupIfVisible() {
        try {
            waitUntilElementClickable(xButton).click();
        } catch (TimeoutException e) {
            // For some scenarios the button is not visible and doesn't need to be clicked
        }
        return new HomeScreen(driver);
    }

    public List<String> getLoggedInAccounts() {
        if (driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")) {
            try {
                // on iOS some scenarios require expanding the account list
                waitUntilElementClickable(currentAcc).click();
            } catch (TimeoutException e) {
                // For some scenarios the button is not visible and doesn't need to be clicked
            }
        }

        List<String> accounts = new ArrayList<>();

        waitUntilAllElementsVisible(loggedInAccounts);

        if (!loggedInAccounts.isEmpty()) {
            // Using split to support the element format on iOS
            loggedInAccounts.forEach(element -> accounts
                    .addAll(
                            Arrays.asList(element.getText()
                            .split(", |\\. ")
                            )
                    ));
        }

        return accounts;
    }

    public HomeScreen logInIfAccNotPresent(String login, String password) {
        if (driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")) {
            waitUntilElementClickable(currentAcc).click();
        }

        waitUntilAllElementsVisible(loggedInAccounts);

        MobileElement correctAcc = findElementContaining(login, loggedInAccounts);

        if (correctAcc == null) {
            // For scenarios with other accounts logged in
            try {
                waitUntilElementClickable(addAccBtn).click();
                if (driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")) {
                    waitUntilElementClickable(continueBtn).click();
                }
            } catch (TimeoutException e) {
                // For other scenarios the buttons are not visible and don't need to be clicked
            }
            if (driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")) {
                try {
                    // Some scenarios require expanding the account list again
                    waitUntilElementClickable(currentAcc).click();
                    waitUntilElementClickable(addAccBtn).click();
                    waitUntilElementClickable(continueBtn).click();
                } catch (TimeoutException e) {
                    // For other scenarios the button is not visible and doesn't need to be clicked
                }
            }

            Helpers.logIn(login, password);
        } else {
            correctAcc.click();
        }

        closeAccPopupIfVisible();
        return new HomeScreen(driver);
    }

    public HomeScreen logoutIfAccPresent(String login) {
        if (driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")) {
            try {
                // On iOS click current account to expand list of logged in accounts
                waitUntilElementClickable(currentAcc).click();
            } catch (TimeoutException e) {
                // For other scenarios the button is not visible and doesn't need to be clicked
            }
        }

        waitUntilAllElementsVisible(loggedInAccounts);

        MobileElement correctAcc = findElementContaining(login, loggedInAccounts);

        // For scenarios where the correct account is logged in
        if (correctAcc != null) {
            if (driver.getClass().getTypeName().equals("io.appium.java_client.android.AndroidDriver")) {
                waitUntilElementClickable(manageAccountsBtn).click();
            } else if (driver.getClass().getTypeName().equals("io.appium.java_client.ios.IOSDriver")
                    && !waitUntilElementVisible(currentAcc).getText().contains(login)) {
                // Case 1 for iOS, where the account is not the current one
                waitUntilElementClickable(correctAcc).click();
                new HomeScreen(driver).tapUserIcon();
                waitUntilElementClickable(googleAccountBtn).click();
            } else {
                // Case 2 for iOS where the account is the current one
                waitUntilElementClickable(googleAccountBtn).click();
            }
            Helpers.logOut(login);
        }

        return new HomeScreen(driver);
    }

}
