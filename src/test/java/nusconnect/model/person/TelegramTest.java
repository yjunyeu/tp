package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TelegramTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Telegram(null));
    }

    @Test
    public void isValidTelegram() {
        // null Telegram
        assertThrows(NullPointerException.class, () -> Telegram.isValidTelegram(null));

        // invalid Telegram
        assertFalse(Telegram.isValidTelegram("")); // empty string
        assertFalse(Telegram.isValidTelegram(" ")); // spaces only
        assertFalse(Telegram.isValidTelegram("^")); // only non-alphanumeric characters
        assertFalse(Telegram.isValidTelegram("peter*")); // contains non-alphanumeric characters
        assertFalse(Telegram.isValidTelegram("@")); // @ but without names
        assertFalse(Telegram.isValidTelegram("peter"));
        assertFalse(Telegram.isValidTelegram("@bob")); // not enough characters
        assertFalse(Telegram.isValidTelegram("@David_Roger_Jackson_Ray_Hamilton_Jr_2nd")); // excess characters

        // valid Telegram
        assertTrue(Telegram.isValidTelegram("@peter_jack")); // alphabets only
        assertTrue(Telegram.isValidTelegram("@12345")); // numbers only
        assertTrue(Telegram.isValidTelegram("@peter_the_2nd")); // alphanumeric characters
        assertTrue(Telegram.isValidTelegram("@Capital_Tan")); // with capital letters
        assertTrue(Telegram.isValidTelegram("@David_Roger_Jackson_Ray_Jr_2nd")); // long Telegrams
    }

    @Test
    public void equals() {
        Telegram telegram = new Telegram("@validtelegram");

        // same values -> returns true
        assertTrue(telegram.equals(new Telegram("@validtelegram")));

        // same object -> returns true
        assertTrue(telegram.equals(telegram));

        // null -> returns false
        assertFalse(telegram.equals(null));

        // different types -> returns false
        assertFalse(telegram.equals(5.0f));

        // different values -> returns false
        assertFalse(telegram.equals("@othervalidtelegram"));
    }
}
