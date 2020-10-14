package com.twoupdigital.googlekeep;

import com.twoupdigital.googlekeep.pageobjects.basescreen.screenwithnotecells.HomeScreen;
import org.junit.jupiter.api.Test;

import static com.twoupdigital.googlekeep.utils.Helpers.generateRandomAlphanumericStringWithLengthBetween;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoteEditTests extends BaseTest {

    @Test
    void t002NoteEditsCorrectly() {
        String correctTitle = generateRandomAlphanumericStringWithLengthBetween(10, 16);
        String correctNote = generateRandomAlphanumericStringWithLengthBetween(40, 60);
        String correctEditedTitle = generateRandomAlphanumericStringWithLengthBetween(7, 9);
        String correctEditedNote = generateRandomAlphanumericStringWithLengthBetween(30, 39);

        new HomeScreen(driver)
                .tapAddNote()
                .enterTitle(correctTitle)
                .enterNote(correctNote)
                .tapBack()
                .tapNoteTitled(correctTitle)
                .enterTitle(correctEditedTitle)
                .enterNote(correctEditedNote)
                .tapBack();

        HomeScreen homeScreen = new HomeScreen(driver);

        assertTrue(homeScreen.getNoteTitleContaining(correctEditedTitle).isDisplayed());
        assertTrue(homeScreen.getNoteDescContaining(correctEditedNote).isDisplayed());
        assertTrue(homeScreen.getNoteTitleContaining(correctEditedTitle).getText().contains(correctEditedTitle));
        assertTrue(homeScreen.getNoteDescContaining(correctEditedNote).getText().contains(correctEditedNote));
    }

}
