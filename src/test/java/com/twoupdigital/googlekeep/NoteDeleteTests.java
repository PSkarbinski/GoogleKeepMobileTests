package com.twoupdigital.googlekeep;

import com.twoupdigital.googlekeep.pageobjects.basescreen.screenwithnotecells.HomeScreen;
import org.junit.jupiter.api.Test;

import static com.twoupdigital.googlekeep.utils.Helpers.generateRandomAlphanumericStringWithLengthBetween;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NoteDeleteTests extends BaseTest {

    @Test
    void t003NoteDeletesCorrectlyFromInsideNote() {
        String correctTitle = generateRandomAlphanumericStringWithLengthBetween(10, 16);
        String correctNote = generateRandomAlphanumericStringWithLengthBetween(40, 60);

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
