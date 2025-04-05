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
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // Equivalence partitioning:
        // [string]
        // [numbers with length 8] [everything else]
        // [numbers starting with 6,8,9 and length 8] [strings starting with other numbers (len 8)] [else]
        // So, 60000000 to 69999999, 80000000 to 99999999

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("8765432")); // not enough digits
        assertFalse(Phone.isValidPhone("987654321")); // excess digits
        assertFalse(Phone.isValidPhone("889977665544332211")); // excess digits
        assertFalse(Phone.isValidPhone("76543210")); // illegal start digit
        assertFalse(Phone.isValidPhone("01234567")); // illegal start digit
        assertFalse(Phone.isValidPhone("34567890")); // Valid VOIP number: No NUS student would use this

        // boundary values

        assertFalse(Phone.isValidPhone("59999999"));
        assertFalse(Phone.isValidPhone("70000000"));
        assertFalse(Phone.isValidPhone("79999999"));
        assertFalse(Phone.isValidPhone("100000000")); //not correct length as well

        // valid phone numbers
        assertTrue(Phone.isValidPhone("67654321")); // starts with 9, len 8, only numbers
        assertTrue(Phone.isValidPhone("87654321")); // starts with 9, len 8, only numbers
        assertTrue(Phone.isValidPhone("97654321")); // starts with 9, len 8, only numbers

        // Boundary Values
        assertTrue(Phone.isValidPhone("60000000")); // starts with 9, len 8, only numbers
        assertTrue(Phone.isValidPhone("80000000")); // starts with 9, len 8, only numbers

        assertTrue(Phone.isValidPhone("69999999")); // starts with 9, len 8, only numbers
        assertTrue(Phone.isValidPhone("99999999")); // starts with 9, len 8, only numbers
    }

    @Test
    public void equals() {
        Phone phone = new Phone("99999999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("99999999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("99599599")));
    }
}
