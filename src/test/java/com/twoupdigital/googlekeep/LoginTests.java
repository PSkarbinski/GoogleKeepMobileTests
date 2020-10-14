package com.twoupdigital.googlekeep;

import com.twoupdigital.googlekeep.pageobjects.AccountPopup;
import com.twoupdigital.googlekeep.pageobjects.basescreen.screenwithnotecells.HomeScreen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests extends BaseTest {

    @BeforeEach
    // These tests don't need to be logged in from start, overriding setUp()
    void setUpEach() {
        driver.launchApp();
    }

    @AfterEach
    // These tests don't require note deletion, overriding cleanUpEach
    void cleanUpEach() {
        driver.closeApp();
    }

    @Test
    void t0004LogInCorrectly() {
        new HomeScreen(driver)
                .tapGetStartedIfVisible()
                .logoutIfAccPresent(googleAcc)
                .tapGetStartedIfVisible()
                .logInIfAccNotPresent(googleAcc, googlePw)
                .tapGetStartedIfVisible()
                .tapUserIcon();

        AccountPopup accountPopup = new AccountPopup(driver);
        HomeScreen homeScreen = new HomeScreen(driver);

        assertTrue(accountPopup.getLoggedInAccounts().contains(googleAcc));
        accountPopup.closeAccPopupIfVisible();
        assertTrue(homeScreen.getAddNoteBtn().isDisplayed());
        assertTrue(homeScreen.getNavMenuBtn().isDisplayed());
    }

}
