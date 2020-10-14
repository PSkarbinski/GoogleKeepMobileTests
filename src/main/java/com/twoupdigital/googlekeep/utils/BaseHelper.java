package com.twoupdigital.googlekeep.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class BaseHelper {

    protected static AppiumDriver<MobileElement> driver;

    public static void setDriver(AppiumDriver<MobileElement> driver) {
        BaseHelper.driver = driver;
    }

}
