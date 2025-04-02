package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Major(null));
    }

    @Test
    public void isValidCourse() {
        // null course
        assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid course
        assertFalse(Major.isValidMajor(new Major(""))); // empty string
        assertFalse(Major.isValidMajor(new Major(" "))); // spaces only
        assertFalse(Major.isValidMajor(new Major("^"))); // only non-alphabets
        assertFalse(Major.isValidMajor(new Major("Computer*Science"))); // contains non-alphabets

        // valid course
        assertTrue(Major.isValidMajor(new Major("computer science"))); // alphabets only
        assertTrue(Major.isValidMajor(new Major("Computer Science"))); // with capital letters
        assertTrue(Major.isValidMajor(new Major("Computer Science with Mathematics"))); // long course name
    }

    @Test
    public void equals() {
        Major course = new Major("Valid Major");

        // same values -> returns true
        assertTrue(course.equals(new Major("Valid Major")));

        // same object -> returns true
        assertTrue(course.equals(course));

        // null -> returns false
        assertFalse(course.equals(null));

        // different types -> returns false
        assertFalse(course.equals(5.0f));

        // different values -> returns false
        assertFalse(course.equals(new Major("Other Valid Major")));
    }
}
