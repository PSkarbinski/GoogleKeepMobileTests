package com.pskarbinski.googlekeep;

import com.pskarbinski.googlekeep.pageobjects.basescreen.screenwithnotecells.ArchiveScreen;
import com.pskarbinski.googlekeep.pageobjects.basescreen.screenwithnotecells.HomeScreen;
import com.pskarbinski.googlekeep.utils.Helpers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoteArchiveTests extends BaseTest {

    @Test
    void t0005NoteArchivesCorrectlyFromInsideNote() {
        String correctTitle = Helpers.generateRandomAlphanumericStringWithLengthBetween(10, 16);
        String correctNote = Helpers.generateRandomAlphanumericStringWithLengthBetween(40, 60);

        new HomeScreen(driver)
                .tapAddNote()
                .enterTitle(correctTitle)
                .enterNote(correctNote)
                .tapBack()
                .tapNoteTitled(correctTitle)
                .tapArchive()
                .tapNavMenu()
                .tapNavMenuArchive();

        ArchiveScreen archiveScreen = new ArchiveScreen(driver);

        assertTrue(archiveScreen.getNoteTitleContaining(correctTitle).isDisplayed());
        assertTrue(archiveScreen.getNoteDescContaining(correctNote).isDisplayed());
        assertTrue(archiveScreen.getNoteTitleContaining(correctTitle).getText().contains(correctTitle));
        assertTrue(archiveScreen.getNoteDescContaining(correctNote).getText().contains(correctNote));
    }

}
