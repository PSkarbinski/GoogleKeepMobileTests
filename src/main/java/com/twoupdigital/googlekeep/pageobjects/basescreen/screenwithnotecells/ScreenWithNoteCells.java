package com.twoupdigital.googlekeep.pageobjects.basescreen.screenwithnotecells;

import com.twoupdigital.googlekeep.pageobjects.basescreen.NoteScreen;
import com.twoupdigital.googlekeep.pageobjects.basescreen.BaseScreen;
import com.twoupdigital.googlekeep.utils.MobileActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;
import java.util.Objects;

import static com.twoupdigital.googlekeep.utils.Finders.findElementContaining;
import static com.twoupdigital.googlekeep.utils.Waits.waitUntilAllElementsVisible;
import static com.twoupdigital.googlekeep.utils.Waits.waitUntilElementClickable;

public abstract class ScreenWithNoteCells extends BaseScreen {

    @AndroidFindBy(id = "com.google.android.keep:id/index_note_title")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCell")
    List<MobileElement> noteTitlesList;

    @AndroidFindBy(id = "com.google.android.keep:id/index_note_text_description")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCell")
    List<MobileElement> noteDescList;

    @AndroidFindBy(accessibility = "More options")
    @iOSXCUITFindBy(accessibility = "More options")
    MobileElement dotsMenuBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Delete']")
    @iOSXCUITFindBy(accessibility = "Delete")
    MobileElement dotsMenuDeleteBtn;

    public ScreenWithNoteCells(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public MobileElement getNoteTitleContaining(String title) {
        try {
            waitUntilAllElementsVisible(noteTitlesList);
        } catch (TimeoutException e) {
            // For some scenarios there shouldn't be any notes
        }

        if (!noteTitlesList.isEmpty()) {
            return findElementContaining(title, noteTitlesList);
        } else {
            return null;
        }
    }

    public NoteScreen tapNoteTitled(String title) {
        waitUntilAllElementsVisible(noteTitlesList);

        if (!noteTitlesList.isEmpty()) {
            Objects.requireNonNull(findElementContaining(title, noteTitlesList)).click();
            return new NoteScreen(driver);
        } else {
            return null;
        }
    }

    public MobileElement getNoteDescContaining(String desc) {
        try {
            waitUntilAllElementsVisible(noteDescList);
        } catch (TimeoutException e) {
            // For some scenarios there shouldn't be any notes
        }

        if (!noteDescList.isEmpty()) {
            return findElementContaining(desc, noteDescList);
        } else {
            return null;
        }
    }

    public ScreenWithNoteCells deleteAllNotes() {
        try {
            waitUntilAllElementsVisible(noteTitlesList);
        } catch (TimeoutException e) {
            // For some scenarios there shouldn't be any notes
        }

        if (!noteTitlesList.isEmpty()) {
            noteTitlesList
                    .stream()
                    .findFirst()
                    .ifPresent(MobileActions::longPress);

            if (noteTitlesList.size() > 1) {
                noteTitlesList
                        .stream()
                        .skip(1)
                        .forEach(RemoteWebElement::click);
            }

            waitUntilElementClickable(dotsMenuBtn).click();
            waitUntilElementClickable(dotsMenuDeleteBtn).click();
        }

        return this;
    }

}
