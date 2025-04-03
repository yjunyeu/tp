package nusconnect.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's telegram in the telegram book.
 * Guarantees: immutable
 */
public class Telegram {

    public static final String MESSAGE_CONSTRAINTS =
            "Telegram handles must start with an @, then must be at least 5 characters long, case-insensitive, \n"
                    + "and can only contain letters, numbers, underscores, and it should not be blank!";

    /*
     * The first character of the telegram must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^@[a-zA-Z0-9_]{5,}$";

    public final String value;

    /**
     * Constructs an {@code Telegram}.
     */
    public Telegram(String telegram) {
        requireNonNull(telegram);
        if (telegram.isEmpty()) {
            value = "<no handle>";
        } else {
            value = telegram;
        }
    }

    /**
     * Returns true if a given Telegram is a valid telegram handle.
     */
    public static boolean isValidTelegram(Telegram test) {
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
        if (!(other instanceof Telegram)) {
            return false;
        }

        Telegram otherTelegram = (Telegram) other;
        return value.equals(otherTelegram.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
