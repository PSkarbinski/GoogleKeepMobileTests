package com.pskarbinski.googlekeep.pageobjects.basescreen.screenwithnotecells;

import com.pskarbinski.googlekeep.pageobjects.basescreen.BaseScreen;
import com.pskarbinski.googlekeep.utils.Waits;
import com.pskarbinski.googlekeep.pageobjects.basescreen.NoteScreen;
import com.pskarbinski.googlekeep.utils.MobileActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;
import java.util.Objects;

import static com.pskarbinski.googlekeep.utils.Finders.findElementContaining;
import static com.pskarbinski.googlekeep.utils.Waits.waitUntilElementClickable;

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
            Waits.waitUntilAllElementsVisible(noteTitlesList);
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
        Waits.waitUntilAllElementsVisible(noteTitlesList);

        if (!noteTitlesList.isEmpty()) {
            Objects.requireNonNull(findElementContaining(title, noteTitlesList)).click();
            return new NoteScreen(driver);
        } else {
            return null;
        }
    }

    public MobileElement getNoteDescContaining(String desc) {
        try {
            Waits.waitUntilAllElementsVisible(noteDescList);
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
            Waits.waitUntilAllElementsVisible(noteTitlesList);
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

            Waits.waitUntilElementClickable(dotsMenuBtn).click();
            Waits.waitUntilElementClickable(dotsMenuDeleteBtn).click();
        }

        return this;
    }

}
