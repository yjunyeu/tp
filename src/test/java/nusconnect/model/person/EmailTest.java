package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail(new Email(""))); // empty string
        assertFalse(Email.isValidEmail(new Email(" "))); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail(new Email("@example.com"))); // missing local part
        assertFalse(Email.isValidEmail(new Email("peterjackexample.com"))); // missing '@' symbol
        assertFalse(Email.isValidEmail(new Email("peterjack@"))); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail(new Email("peterjack@-"))); // invalid domain name
        assertFalse(Email.isValidEmail(new Email("peterjack@exam_ple.com"))); // underscore in domain name
        assertFalse(Email.isValidEmail(new Email("peter jack@example.com"))); // spaces in local part
        assertFalse(Email.isValidEmail(new Email("peterjack@exam ple.com"))); // spaces in domain name
        assertFalse(Email.isValidEmail(new Email(" peterjack@example.com"))); // leading space
        assertFalse(Email.isValidEmail(new Email("peterjack@example.com "))); // trailing space
        assertFalse(Email.isValidEmail(new Email("peterjack@@example.com"))); // double '@' symbol
        assertFalse(Email.isValidEmail(new Email("peter@jack@example.com"))); // '@' symbol in local part
        assertFalse(Email.isValidEmail(new Email("-peterjack@example.com"))); // local part starts with a hyphen
        assertFalse(Email.isValidEmail(new Email("peterjack-@example.com"))); // local part ends with a hyphen
        assertFalse(Email.isValidEmail(new Email("peter..jack@example.com"))); // local part has two consecutive periods
        assertFalse(Email.isValidEmail(new Email("peterjack@example@com"))); // '@' symbol in domain name
        assertFalse(Email.isValidEmail(new Email("peterjack@.example.com"))); // domain name starts with a period
        assertFalse(Email.isValidEmail(new Email("peterjack@example.com."))); // domain name ends with a period
        assertFalse(Email.isValidEmail(new Email("peterjack@-example.com"))); // domain name starts with a hyphen
        assertFalse(Email.isValidEmail(new Email("peterjack@example.com-"))); // domain name ends with a hyphen
        assertFalse(Email.isValidEmail(new Email("peterjack@example.c"))); // top level domain has less than two chars

        // valid email
        assertTrue(Email.isValidEmail(new Email("PeterJack_1190@example.com"))); // underscore in local part
        assertTrue(Email.isValidEmail(new Email("PeterJack.1190@example.com"))); // period in local part
        assertTrue(Email.isValidEmail(new Email("PeterJack+1190@example.com"))); // '+' symbol in local part
        assertTrue(Email.isValidEmail(new Email("PeterJack-1190@example.com"))); // hyphen in local part
        assertTrue(Email.isValidEmail(new Email("a@bc"))); // minimal
        assertTrue(Email.isValidEmail(new Email("test@localhost"))); // alphabets only
        assertTrue(Email.isValidEmail(new Email("123@145"))); // numeric local part and domain name
        assertTrue(Email.isValidEmail(new Email("a1+be.d@example1.com"))); // mix of alphanumeric and special characters
        assertTrue(Email.isValidEmail(new Email("peter_jack@very-very-very-long-example.com"))); // long domain name
        assertTrue(Email.isValidEmail(new Email("if.you.dream.it_you.can.do.it@example.com"))); // long local part
        assertTrue(Email.isValidEmail(new Email("e1234567@u.nus.edu"))); // more than one period in domain

    }

    @Test
    public void equals() {
        Email email = new Email("valid@email");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email")));
    }
}
