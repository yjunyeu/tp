package nusconnect.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's alias in the alias book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAlias(String)}
 */
public class Alias {

    public static final String MESSAGE_CONSTRAINTS =
            "Aliases can only contain alphabets, numbers, underlines, spaces, and it should not be blank!";

    /*
     * The first character of the alias must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z0-9][A-Za-z0-9_ ]*";

    public final String value;

    /**
     * Constructs an {@code Alias}.
     *
     * @param alias A valid alias.
     */
    public Alias(String alias) {
        requireNonNull(alias);
        value = alias;
    }

    /**
     * Returns true if a given string is a valid alias.
     */
    public static boolean isValidAlias(Alias test) {
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
        if (!(other instanceof Alias)) {
            return false;
        }

        Alias otherAlias = (Alias) other;
        return value.equals(otherAlias.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
