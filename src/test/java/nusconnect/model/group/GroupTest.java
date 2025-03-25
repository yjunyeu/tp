package nusconnect.model.group;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nusconnect.commons.util.ToStringBuilder;
import nusconnect.model.person.Person;
import nusconnect.testutil.PersonBuilder;

public class GroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Group(null));
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidGroupName = "";
        assertThrows(IllegalArgumentException.class, () -> new Group(invalidGroupName));
    }

    @Test
    public void isValidGroupName() {
        // null group name
        assertThrows(NullPointerException.class, () -> Group.isValidGroupName(null));

        // invalid group name
        assertFalse(Group.isValidGroupName("")); // empty string
        assertFalse(Group.isValidGroupName(" ")); // spaces only
        assertFalse(Group.isValidGroupName("^")); // only non-alphanumeric characters
        assertFalse(Group.isValidGroupName("cs*2103")); // contains non-alphanumeric characters

        // valid group name
        assertTrue(Group.isValidGroupName("CS2103")); // alphabets and numbers only
        assertTrue(Group.isValidGroupName("CS2103T")); // with uppercase
        assertTrue(Group.isValidGroupName("Project Group 1")); // with spaces
        assertTrue(Group.isValidGroupName("Software Engineering")); // with space
    }

    @Test
    public void getGroupName() {
        Group group = new Group("CS2103T");
        assertEquals("CS2103T", group.getGroupName());
    }

    @Test
    public void getMembers_modifyList_throwsUnsupportedOperationException() {
        Group group = new Group("CS2103T");
        assertThrows(UnsupportedOperationException.class, () -> group.getMembers().clear());
    }

    @Test
    public void hasMember() {
        Group group = new Group("CS2103T");
        Person person = new PersonBuilder().build();

        // Group initially has no members
        assertFalse(group.hasMember(person));

        // After adding a member, hasMember should return true
        group.addMember(person);
        assertTrue(group.hasMember(person));

        // After removing the member, hasMember should return false
        group.removeMember(person);
        assertFalse(group.hasMember(person));
    }

    @Test
    public void equals() {
        Group cs2103 = new Group("CS2103");

        // same values -> returns true
        assertTrue(cs2103.equals(new Group("CS2103")));

        // same object -> returns true
        assertTrue(cs2103.equals(cs2103));

        // null -> returns false
        assertFalse(cs2103.equals(null));

        // different type -> returns false
        assertFalse(cs2103.equals(5));

        // different name -> returns false
        assertFalse(cs2103.equals(new Group("CS2106")));
    }

    @Test
    public void toString_test() {
        Group group = new Group("CS2103T");
        String expected = new ToStringBuilder(group)
                .add("groupName", group.getGroupName())
                .add("memberCount", 0)
                .toString();

        assertEquals(expected, group.toString());
    }
}
