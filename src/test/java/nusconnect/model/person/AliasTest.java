package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AliasTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Alias(null));
    }

    @Test
    public void constructor_invalidAlias_throwsIllegalArgumentException() {
        String invalidAlias = "";
        assertThrows(IllegalArgumentException.class, () -> new Alias(invalidAlias));
    }

    @Test
    public void isValidAlias() {
        // null alias
        assertThrows(NullPointerException.class, () -> Alias.isValidAlias(null));

        // invalid alias
        assertFalse(Alias.isValidAlias("")); // empty string
        assertFalse(Alias.isValidAlias(" ")); // spaces only
        assertFalse(Alias.isValidAlias("^")); // only non-alphanumeric characters

        // valid alias
        assertTrue(Alias.isValidAlias("peter jack")); // alphabets only
        assertTrue(Alias.isValidAlias("12345")); // numbers only
        assertTrue(Alias.isValidAlias("peter the 2nd")); // alphanumeric characters
        assertTrue(Alias.isValidAlias("Capital Tan")); // with capital letters
        assertTrue(Alias.isValidAlias("David Roger Jackson Ray Jr 2nd")); // long aliass
    }

    @Test
    public void equals() {
        Alias alias = new Alias("Valid Alias");

        // same values -> returns true
        assertTrue(alias.equals(new Alias("Valid Alias")));

        // same object -> returns true
        assertTrue(alias.equals(alias));

        // null -> returns false
        assertFalse(alias.equals(null));

        // different types -> returns false
        assertFalse(alias.equals(5.0f));

        // different values -> returns false
        assertFalse(alias.equals(new Alias("Other Valid Alias")));
    }
}
