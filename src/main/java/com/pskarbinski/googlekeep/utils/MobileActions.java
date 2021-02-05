package com.pskarbinski.googlekeep.utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;

public class MobileActions extends BaseHelper {

    private static final PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    // Using this method, because TouchAction's longPress() is bugged with Appium 1.18.0-2
    public static void longPress(MobileElement element) {
        Interaction moveToElement = finger.createPointerMove(Duration.ZERO, PointerInput.Origin.fromElement(element),0,0);
        Interaction pressDown = finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg());
        Interaction holdDown = finger.createPointerMove(ofMillis(1000), PointerInput.Origin.fromElement(element), 0, 0);
        Interaction pressUp = finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg());

        Sequence longPress = new Sequence(finger, 0);
        longPress.addAction(moveToElement);
        longPress.addAction(pressDown);
        longPress.addAction(holdDown);
        longPress.addAction(pressUp);

        driver.perform(Collections.singletonList(longPress));
    }

    public static WebElement swipeDownMaxTimesAndFindBy(int maxTimes, By locator) {
        List<WebElement> elements = new ArrayList<>();
        TouchAction<?> touchAction = new TouchAction<>(driver);

        int startX = (driver.manage().window().getSize().getHeight() / 2);
        int startY = (int) (driver.manage().window().getSize().getHeight() * 0.70);
        int endY = (int) (driver.manage().window().getSize().getHeight() * 0.30);

        int i = 0;

        while (elements.size() == 0 && i < maxTimes) {
            touchAction
                    .press(PointOption.point(startX,startY))
                    .waitAction(waitOptions(ofMillis(800)))
                    .moveTo(PointOption.point(startX,endY))
                    .release()
                    .perform();
            try {
                elements.add(driver.findElement(locator));
            } catch (NoSuchElementException e) {
                // Script keeps scrolling if element is not found
            }

            i++;
        }

        if (!elements.isEmpty()) {
            return elements.get(0);
        } else {
            return null;
        }
    }

}
