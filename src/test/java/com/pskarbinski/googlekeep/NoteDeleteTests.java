package com.pskarbinski.googlekeep;

import com.pskarbinski.googlekeep.pageobjects.basescreen.screenwithnotecells.HomeScreen;
import com.pskarbinski.googlekeep.utils.Helpers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class NoteDeleteTests extends BaseTest {

    @Test
    void t003NoteDeletesCorrectlyFromInsideNote() {
        String correctTitle = Helpers.generateRandomAlphanumericStringWithLengthBetween(10, 16);
        String correctNote = Helpers.generateRandomAlphanumericStringWithLengthBetween(40, 60);

        new HomeScreen(driver)
                .tapAddNote()
                .enterTitle(correctTitle)
                .enterNote(correctNote)
                .tapBack()
                .tapNoteTitled(correctTitle)
                .tapDotsMenu()
                .tapDelete();

        HomeScreen homeScreen = new HomeScreen(driver);

        assertNull(homeScreen.getNoteTitleContaining(correctTitle));
        assertNull(homeScreen.getNoteDescContaining(correctNote));
    }

}
