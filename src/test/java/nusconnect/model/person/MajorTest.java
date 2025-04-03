package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MajorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Major(null));
    }

    @Test
    public void constructor_invalidMajor_throwsIllegalArgumentException() {
        String invalidMajor = "";
        assertThrows(IllegalArgumentException.class, () -> new Major(invalidMajor));
    }

    @Test
    public void isValidMajor() {
        // null major
        assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid major
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor(" ")); // spaces only
        assertFalse(Major.isValidMajor("^")); // only non-alphabets
        assertFalse(Major.isValidMajor("Computer*Science")); // contains non-alphanbets

        // valid major
        assertTrue(Major.isValidMajor("computer science")); // alphabets only
        assertTrue(Major.isValidMajor("Computer Science")); // with capital letters
        assertTrue(Major.isValidMajor("Computer Science with a second major in Mathematics")); // long major
    }

    @Test
    public void equals() {
        Major major = new Major("Valid Major");

        // same values -> returns true
        assertTrue(major.equals(new Major("Valid Major")));

        // same object -> returns true
        assertTrue(major.equals(major));

        // null -> returns false
        assertFalse(major.equals(null));

        // different types -> returns false
        assertFalse(major.equals(5.0f));

        // different values -> returns false
        assertFalse(major.equals(new Major("Other Valid Major")));
    }
}
