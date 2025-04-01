package nusconnect.model.person;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Course(null));
    }

    @Test
    public void isValidCourse() {
        // null course
        assertThrows(NullPointerException.class, () -> Course.isValidCourse(null));

        // invalid course
        assertFalse(Course.isValidCourse(new Course(""))); // empty string
        assertFalse(Course.isValidCourse(new Course(" "))); // spaces only
        assertFalse(Course.isValidCourse(new Course("^"))); // only non-alphabets
        assertFalse(Course.isValidCourse(new Course("Computer*Science"))); // contains non-alphabets

        // valid course
        assertTrue(Course.isValidCourse(new Course("computer science"))); // alphabets only
        assertTrue(Course.isValidCourse(new Course("Computer Science"))); // with capital letters
        assertTrue(Course.isValidCourse(new Course("Computer Science with Mathematics"))); // long course name
    }

    @Test
    public void equals() {
        Course course = new Course("Valid Course");

        // same values -> returns true
        assertTrue(course.equals(new Course("Valid Course")));

        // same object -> returns true
        assertTrue(course.equals(course));

        // null -> returns false
        assertFalse(course.equals(null));

        // different types -> returns false
        assertFalse(course.equals(5.0f));

        // different values -> returns false
        assertFalse(course.equals(new Course("Other Valid Course")));
    }
}
