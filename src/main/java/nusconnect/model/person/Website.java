package nusconnect.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's website in the address book.
 * Guarantees: immutable
 */
public class Website {

    public static final String MESSAGE_CONSTRAINTS =
            "Websites start with 'http://' or 'https://', followed by a valid domain name!";

    // Regex for validating website format
    public static final String VALIDATION_REGEX = "^(https?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}(/[\\w-]*)*$";

    public final String value;

    /**
     * Constructs a {@code Website}.
     */
    public Website(String website) {
        requireNonNull(website, "Website cannot be null");
        value = website;
    }

    /**
     * Returns true if a given Website is a valid website.
     */
    public static boolean isValidWebsite(Website test) {
        return test.value.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof Website)) {
            return false;
        }

        Website otherWebsite = (Website) other;
        return value.equals(otherWebsite.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
