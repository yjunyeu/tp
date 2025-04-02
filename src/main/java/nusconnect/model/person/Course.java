package nusconnect.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's course in the course book.
 * Guarantees: immutable
 */
public class Course {

    public static final String MESSAGE_CONSTRAINTS =
            "Courses can only contain alphabets, spaces, and it should not be blank!";

    /*
     * The first character of the course must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z][A-Za-z ]*";

    public final String value;

    /**
     * Constructs an {@code Course}.
     */
    public Course(String course) {
        requireNonNull(course);
        value = course;
    }

    /**
     * Returns true if a given Course is a valid course.
     */
    public static boolean isValidCourse(Course test) {
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
        if (!(other instanceof Course)) {
            return false;
        }

        Course otherCourse = (Course) other;
        return value.equals(otherCourse.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
