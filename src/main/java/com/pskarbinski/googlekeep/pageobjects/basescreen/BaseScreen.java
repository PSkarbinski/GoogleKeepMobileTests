package com.pskarbinski.googlekeep.pageobjects.basescreen;

import com.pskarbinski.googlekeep.pageobjects.basescreen.screenwithnotecells.ArchiveScreen;
import com.pskarbinski.googlekeep.utils.Waits;
import com.pskarbinski.googlekeep.pageobjects.basescreen.screenwithnotecells.TrashBinScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import static com.pskarbinski.googlekeep.utils.Waits.waitUntilElementClickable;

public abstract class BaseScreen {

    protected AppiumDriver<MobileElement> driver;

    @AndroidFindBy(accessibility = "Open navigation drawer")
    @iOSXCUITFindBy(accessibility = "Navigation menu")
    MobileElement navMenuBtn;

    @AndroidFindBy(id = "com.google.android.keep:id/drawer_navigation_archive")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCell[@name='Archive']")
    MobileElement navMenuArchiveBtn;

    @AndroidFindBy(id = "com.google.android.keep:id/drawer_navigation_trash")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCell[@name='Bin']")
    MobileElement navMenuTrashBinBtn;

    public BaseScreen(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public MobileElement getNavMenuBtn() {
        return Waits.waitUntilElementClickable(navMenuBtn);
    }

    public BaseScreen tapNavMenu() {
        Waits.waitUntilElementClickable(navMenuBtn).click();
        return this;
    }

    public ArchiveScreen tapNavMenuArchive() {
        Waits.waitUntilElementClickable(navMenuArchiveBtn).click();
        return new ArchiveScreen(driver);
    }

    public TrashBinScreen tapNavMenuTrashBinBtn() {
        Waits.waitUntilElementClickable(navMenuTrashBinBtn).click();
        return new TrashBinScreen(driver);
    }

}
