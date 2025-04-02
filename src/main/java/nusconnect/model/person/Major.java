package nusconnect.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's major in the major book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMajor(Major)}
 */
public class Major {

    public static final String MESSAGE_CONSTRAINTS =
            "Majors can only contain alphabets, spaces, and it should not be blank!";

    /*
     * The first character of the major must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z][A-Za-z ]*";

    public final String value;

    /**
     * Constructs an {@code Major}.
     *
     * @param major A valid major.
     */
    public Major(String major) {
        requireNonNull(major);
        value = major;
    }

    /**
     * Returns true if a given string is a valid major.
     */
    public static boolean isValidMajor(Major test) {
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
        if (!(other instanceof Major)) {
            return false;
        }

        Major otherCourse = (Major) other;
        return value.equals(otherCourse.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
