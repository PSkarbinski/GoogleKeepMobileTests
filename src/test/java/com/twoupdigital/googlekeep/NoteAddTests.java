package com.twoupdigital.googlekeep;

import com.twoupdigital.googlekeep.pageobjects.basescreen.screenwithnotecells.HomeScreen;
import org.junit.jupiter.api.Test;

import static com.twoupdigital.googlekeep.utils.Helpers.generateRandomAlphanumericStringWithLengthBetween;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoteAddTests extends BaseTest {

    @Test
    void t001SimpleNoteAddsCorrectly() {
        String correctTitle = generateRandomAlphanumericStringWithLengthBetween(10, 16);
        String correctNote = generateRandomAlphanumericStringWithLengthBetween(40, 60);

        new HomeScreen(driver)
                .tapAddNote()
                .enterTitle(correctTitle)
                .enterNote(correctNote)
                .tapBack();

        HomeScreen homeScreen = new HomeScreen(driver);

        assertTrue(homeScreen.getNoteTitleContaining(correctTitle).isDisplayed());
        assertTrue(homeScreen.getNoteDescContaining(correctNote).isDisplayed());
        assertTrue(homeScreen.getNoteTitleContaining(correctTitle).getText().contains(correctTitle));
        assertTrue(homeScreen.getNoteDescContaining(correctNote).getText().contains(correctNote));
    }

}
