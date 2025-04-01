package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void isValidNote() {
        // null note
        assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        // invalid note
        assertFalse(Note.isValidNote(new Note(""))); // empty string
        assertFalse(Note.isValidNote(new Note(" "))); // spaces only

        // valid note
        assertTrue(Note.isValidNote(new Note("^"))); // only non-alphanumeric characters
        assertTrue(Note.isValidNote(new Note("peter*"))); // contains non-alphanumeric characters
        assertTrue(Note.isValidNote(new Note("peter jack"))); // alphabets only
        assertTrue(Note.isValidNote(new Note("12345"))); // numbers only
        assertTrue(Note.isValidNote(new Note("peter the 2nd"))); // alphanumeric characters
        assertTrue(Note.isValidNote(new Note("Capital Tan"))); // with capital letters
        assertTrue(Note.isValidNote(new Note("David Roger Jackson Ray Jr 2nd"))); // long notes

    }

    @Test
    public void equals() {
        Note note = new Note("Valid Note");

        // same values -> returns true
        assertTrue(note.equals(new Note("Valid Note")));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        assertFalse(note.equals(new Note("Other Valid Note")));
    }
}
