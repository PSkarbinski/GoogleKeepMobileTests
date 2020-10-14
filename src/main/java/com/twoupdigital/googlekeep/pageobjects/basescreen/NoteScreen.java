package com.twoupdigital.googlekeep.pageobjects.basescreen;

import com.twoupdigital.googlekeep.pageobjects.basescreen.screenwithnotecells.HomeScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.twoupdigital.googlekeep.utils.Waits.waitUntilElementClickable;

public class NoteScreen extends BaseScreen {

    @AndroidFindBy(accessibility = "Open navigation drawer")
    @iOSXCUITFindBy(accessibility = "Go back")
    MobileElement backBtn;

    @AndroidFindBy(accessibility = "Archive")
    @iOSXCUITFindBy(accessibility = "Archive")
    MobileElement archiveBtn;

    @AndroidFindBy(id = "com.google.android.keep:id/editable_title")
    @iOSXCUITFindBy(accessibility = "Title")
    MobileElement titleBox;

    @AndroidFindBy(id = "com.google.android.keep:id/edit_note_text")
    @iOSXCUITFindBy(accessibility = "Note text")
    MobileElement noteBox;

    @AndroidFindBy(accessibility = "Action")
    @iOSXCUITFindBy(accessibility = "Actions menu")
    MobileElement dotsMenuBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Delete']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Delete']")
    MobileElement deleteBtn;

    public NoteScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public NoteScreen enterTitle(String title) {
        waitUntilElementClickable(titleBox).clear();
        waitUntilElementClickable(titleBox).sendKeys(title);
        return this;
    }

    public NoteScreen enterNote(String note) {
        waitUntilElementClickable(noteBox).clear();
        waitUntilElementClickable(noteBox).sendKeys(note);
        return this;
    }

    public HomeScreen tapBack() {
        waitUntilElementClickable(backBtn).click();
        return new HomeScreen(driver);
    }

    public NoteScreen tapDotsMenu() {
        waitUntilElementClickable(dotsMenuBtn).click();
        return this;
    }

    public HomeScreen tapDelete() {
        waitUntilElementClickable(deleteBtn).click();
        return new HomeScreen(driver);
    }

    public HomeScreen tapArchive() {
        waitUntilElementClickable(archiveBtn).click();
        return new HomeScreen(driver);
    }

}
