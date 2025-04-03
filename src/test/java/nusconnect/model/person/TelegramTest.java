package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TelegramTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Telegram(null));
    }

    @Test
    public void blankTelegramIsChangedToNoTelegram() {
        Telegram Telegram = new Telegram("");
        assertEquals("<no handle>", Telegram.toString());
    }

    @Test
    public void isValidTelegram() {
        // null Telegram
        assertThrows(NullPointerException.class, () -> Telegram.isValidTelegram(null));

        // invalid Telegram
        assertFalse(Telegram.isValidTelegram(new Telegram(""))); // should not be blank
        assertFalse(Telegram.isValidTelegram(new Telegram(" "))); // spaces only
        assertFalse(Telegram.isValidTelegram(new Telegram("^"))); // only letters, numbers, underscores
        assertFalse(Telegram.isValidTelegram(new Telegram("@"))); // not at least 5 characters long
        assertFalse(Telegram.isValidTelegram(new Telegram("johndoe"))); // missing @
        assertFalse(Telegram.isValidTelegram(new Telegram("@john"))); // not at least 5 characters long

        // valid Telegram
        assertTrue(Telegram.isValidTelegram(new Telegram("@peterjack"))); // alphabets only
        assertTrue(Telegram.isValidTelegram(new Telegram("@12345"))); // numbers only
        assertTrue(Telegram.isValidTelegram(new Telegram("@peter_the_2nd"))); // alphanumeric characters and underscore
    }

    @Test
    public void equals() {
        Telegram Telegram = new Telegram("@validtelegram");

        // same values -> returns true
        assertTrue(Telegram.equals(new Telegram("@validtelegram")));

        // same object -> returns true
        assertTrue(Telegram.equals(Telegram));

        // null -> returns false
        assertFalse(Telegram.equals(null));

        // different types -> returns false
        assertFalse(Telegram.equals(5.0f));

        // different values -> returns false
        assertFalse(Telegram.equals(new Telegram("@othervalidtelegram")));
    }
}
