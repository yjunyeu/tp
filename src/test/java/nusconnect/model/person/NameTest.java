package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void blankNameIsChangedToNoName () {
        Name name = new Name("");
        assertEquals("<no name>", name.toString());
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName(new Name(""))); // empty string
        assertFalse(Name.isValidName(new Name(" "))); // spaces only
        assertFalse(Name.isValidName(new Name("^"))); // only non-alphanumeric characters
        assertFalse(Name.isValidName(new Name("peter*"))); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName(new Name("peter jack"))); // alphabets only
        assertTrue(Name.isValidName(new Name("12345"))); // numbers only
        assertTrue(Name.isValidName(new Name("peter the 2nd"))); // alphanumeric characters
        assertTrue(Name.isValidName(new Name("Capital Tan"))); // with capital letters
        assertTrue(Name.isValidName(new Name("David Roger Jackson Ray Jr 2nd"))); // long names
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
