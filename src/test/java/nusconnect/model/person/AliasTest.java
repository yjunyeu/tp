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
    public void isValidAlias() {
        // null alias
        assertThrows(NullPointerException.class, () -> Alias.isValidAlias(null));

        // invalid alias
        assertFalse(Alias.isValidAlias(new Alias(""))); // empty string
        assertFalse(Alias.isValidAlias(new Alias(" "))); // spaces only
        assertFalse(Alias.isValidAlias(new Alias("^"))); // only non-alphanumeric characters

        // valid alias
        assertTrue(Alias.isValidAlias(new Alias("peter jack"))); // alphabets only
        assertTrue(Alias.isValidAlias(new Alias("12345"))); // numbers only
        assertTrue(Alias.isValidAlias(new Alias("peter the 2nd"))); // alphanumeric characters
        assertTrue(Alias.isValidAlias(new Alias("Capital Tan"))); // with capital letters
        assertTrue(Alias.isValidAlias(new Alias("David Roger Jackson Ray Jr 2nd"))); // long alias
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
