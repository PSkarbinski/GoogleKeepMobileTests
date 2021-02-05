package com.pskarbinski.googlekeep;

import com.pskarbinski.googlekeep.pageobjects.basescreen.screenwithnotecells.HomeScreen;
import com.pskarbinski.googlekeep.utils.BaseHelper;
import com.pskarbinski.googlekeep.utils.Waits;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class BaseTest {

    public static Properties properties = new Properties();
    protected static boolean propertiesSet = false;
    protected static AppiumDriver<MobileElement> driver;
    protected static String googleAcc;
    protected static String googlePw;

    @BeforeAll
    static void setUp() throws IOException {
        if (!propertiesSet) loadAndSetPropertiesOnce();
        driver = DriverFactory.getDriver();
        BaseHelper.setDriver(driver);
    }

    @BeforeEach
    void setUpEach() {
        driver.launchApp();

        // Log in or switch to the correct account if required
        new HomeScreen(driver)
                .tapGetStartedIfVisible()
                .logInIfAccNotPresent(googleAcc, googlePw)
                .tapGetStartedIfVisible();
    }

    @AfterEach
    void cleanUpEach() {
        new HomeScreen(driver).deleteAllNotes();

        // On slower devices, if the app is closed too fast, note deletion doesn't synchronise and is undone.
        // Circumventing that below.
        new HomeScreen(driver)
                .tapNavMenu()
                .tapNavMenuTrashBinBtn();

        driver.closeApp();
    }

    @AfterAll
    static void cleanUp() {
        driver.quit();
    }

    protected static void loadAndSetPropertiesOnce() throws IOException {
        propertiesSet = true;

        InputStream input = BaseTest.class.getClassLoader().getResourceAsStream("default.properties");
        properties.load(input);
        googleAcc = System.getProperty("googleAcc");
        if (googleAcc == null) googleAcc = properties.getProperty("googleAcc");
        googlePw = System.getProperty("googlePw");

        DriverFactory.setProperties(properties);
        Waits.setProperties(properties);
    }

}
