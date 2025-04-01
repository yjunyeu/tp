package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone(new Phone(""))); // empty string
        assertFalse(Phone.isValidPhone(new Phone(" "))); // spaces only
        assertFalse(Phone.isValidPhone(new Phone("91"))); // less than 3 numbers
        assertFalse(Phone.isValidPhone(new Phone("phone"))); // non-numeric
        assertFalse(Phone.isValidPhone(new Phone("9011p041"))); // alphabets within digits
        assertFalse(Phone.isValidPhone(new Phone("9312 1534"))); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone(new Phone("911"))); // exactly 3 numbers
        assertTrue(Phone.isValidPhone(new Phone("93121534")));
        assertTrue(Phone.isValidPhone(new Phone("124293842033123"))); // long phone numbers
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
}
