package nusconnect.logic.commands;

import static nusconnect.logic.commands.CommandTestUtil.assertCommandFailure;
import static nusconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nusconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nusconnect.model.Model;
import nusconnect.model.ModelManager;
import nusconnect.model.UserPrefs;
import nusconnect.model.group.Group;

public class GroupCreateCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newGroup_success() {
        String validGroupName = "CS2103T";
        GroupCreateCommand groupCreateCommand = new GroupCreateCommand(validGroupName);

        String expectedMessage = String.format(GroupCreateCommand.MESSAGE_SUCCESS, validGroupName);

        expectedModel.addGroup(new Group(validGroupName));

        assertCommandSuccess(groupCreateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        String validGroupName = "CS2103T";

        // First add the group to the model
        model.addGroup(new Group(validGroupName));

        // Try to add the same group again
        GroupCreateCommand groupCreateCommand = new GroupCreateCommand(validGroupName);

        assertCommandFailure(groupCreateCommand, model, GroupCreateCommand.MESSAGE_DUPLICATE_GROUP);
    }

    @Test
    public void equals() {
        String cs2103 = "CS2103";
        String cs2106 = "CS2106";
        GroupCreateCommand addCs2103Command = new GroupCreateCommand(cs2103);
        GroupCreateCommand addCs2106Command = new GroupCreateCommand(cs2106);

        // same object -> returns true
        assertTrue(addCs2103Command.equals(addCs2103Command));

        // same values -> returns true
        GroupCreateCommand addCs2103CommandCopy = new GroupCreateCommand(cs2103);
        assertTrue(addCs2103Command.equals(addCs2103CommandCopy));

        // different types -> returns false
        assertFalse(addCs2103Command.equals(1));

        // null -> returns false
        assertFalse(addCs2103Command.equals(null));

        // different group -> returns false
        assertFalse(addCs2103Command.equals(addCs2106Command));
    }

    @Test
    public void toStringMethod() {
        String groupName = "CS2103T";
        GroupCreateCommand command = new GroupCreateCommand(groupName);
        String expected = GroupCreateCommand.class.getCanonicalName() + "{groupName=" + groupName + "}";
        assertEquals(expected, command.toString());
    }
}
