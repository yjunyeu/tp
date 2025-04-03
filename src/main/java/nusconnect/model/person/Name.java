package nusconnect.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank!";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}. Has a special case for when the name given is empty to ensure minumum
     * understandability of address book.
     */
    public Name(String name) {
        requireNonNull(name);
        fullName = name;
    }

    /**
     * Returns true if a given Name is a valid name.
     */
    public static boolean isValidName(Name test) {
        return test.fullName.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return this.fullName.trim().toLowerCase().replaceAll(" ", "")
                .equals(otherName.fullName.trim().toLowerCase().replaceAll(" ", ""));
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
