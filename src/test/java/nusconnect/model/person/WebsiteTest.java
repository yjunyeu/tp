package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WebsiteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Website(null));
    }

    @Test
    public void constructor_invalidWebsite_throwsIllegalArgumentException() {
        String invalidWebsite = "";
        assertThrows(IllegalArgumentException.class, () -> new Website(invalidWebsite));
    }

    @Test
    public void isValidWebsite() {
        // null website
        assertThrows(NullPointerException.class, () -> Website.isValidWebsite(null));

        // blank website
        assertFalse(Website.isValidWebsite("")); // empty string
        assertFalse(Website.isValidWebsite(" ")); // spaces only

        // missing parts
        assertFalse(Website.isValidWebsite("invalid")); // missing local part

        // invalid parts
        assertFalse(Website.isValidWebsite("invalid.invalidcom")); // invalid domain name

        // valid website
        assertTrue(Website.isValidWebsite("valid.com")); // underscore in local part
    }

    @Test
    public void equals() {
        Website website = new Website("valid.com");

        // same values -> returns true
        assertTrue(website.equals(new Website("valid.com")));

        // same object -> returns true
        assertTrue(website.equals(website));

        // null -> returns false
        assertFalse(website.equals(null));

        // different types -> returns false
        assertFalse(website.equals(5.0f));

        // different values -> returns false
        assertFalse(website.equals(new Website("other.valid.com")));
    }
}
