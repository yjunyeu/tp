package nusconnect.logic.commands;

import static nusconnect.logic.Messages.MESSAGE_INVALID_GROUP_INDEX;
import static nusconnect.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static nusconnect.logic.commands.CommandTestUtil.assertCommandFailure;
import static nusconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;
import static nusconnect.testutil.TypicalIndexes.INDEX_SECOND;
import static nusconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nusconnect.commons.core.index.Index;
import nusconnect.model.AddressBook;
import nusconnect.model.Model;
import nusconnect.model.ModelManager;
import nusconnect.model.UserPrefs;
import nusconnect.model.group.Group;
import nusconnect.model.person.Person;
import nusconnect.testutil.AddressBookBuilder;
import nusconnect.testutil.TypicalGroups;

public class GroupAddCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        AddressBook ab = new AddressBookBuilder(getTypicalAddressBook())
                .withGroup(TypicalGroups.GROUP_CCA)
                .withGroup(TypicalGroups.GROUP_CS2100)
                .build();
        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(ab, new UserPrefs());
    }

    @Test
    public void execute_validIndices_success() {
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Group targetGroup = model.getFilteredGroupList().get(INDEX_SECOND.getZeroBased());
        GroupAddCommand groupAddCommand = new GroupAddCommand(INDEX_FIRST, INDEX_SECOND);

        String expectedMessage = String.format(GroupAddCommand.MESSAGE_SUCCESS,
                personToAdd.getName().fullName, targetGroup.getGroupName());

        Person expectedPersonToAdd = expectedModel.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Group expectedTargetGroup = expectedModel.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        expectedModel.addPersonToGroup(expectedPersonToAdd, expectedTargetGroup);

        assertCommandSuccess(groupAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GroupAddCommand groupAddCommand = new GroupAddCommand(outOfBoundIndex, INDEX_FIRST);

        assertCommandFailure(groupAddCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);
        GroupAddCommand groupAddCommand = new GroupAddCommand(INDEX_FIRST, outOfBoundIndex);

        assertCommandFailure(groupAddCommand, model, MESSAGE_INVALID_GROUP_INDEX);
    }

    @Test
    public void execute_personAlreadyInGroup_throwsCommandException() {
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Group targetGroup = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        model.addPersonToGroup(personToAdd, targetGroup);

        GroupAddCommand groupAddCommand = new GroupAddCommand(INDEX_FIRST, INDEX_FIRST);

        assertCommandFailure(groupAddCommand, model, GroupAddCommand.MESSAGE_PERSON_ALREADY_IN_GROUP);
    }

    @Test
    public void equals() {
        GroupAddCommand addFirstToFirstCommand = new GroupAddCommand(INDEX_FIRST, INDEX_FIRST);
        GroupAddCommand addFirstToSecondCommand = new GroupAddCommand(INDEX_FIRST, INDEX_SECOND);
        GroupAddCommand addSecondToFirstCommand = new GroupAddCommand(INDEX_SECOND, INDEX_FIRST);

        // same object -> returns true
        assertTrue(addFirstToFirstCommand.equals(addFirstToFirstCommand));

        // same values -> returns true
        GroupAddCommand addFirstToFirstCommandCopy = new GroupAddCommand(INDEX_FIRST, INDEX_FIRST);
        assertTrue(addFirstToFirstCommand.equals(addFirstToFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFirstToFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstToFirstCommand.equals(null));

        // different person index -> returns false
        assertFalse(addFirstToFirstCommand.equals(addSecondToFirstCommand));

        // different group index -> returns false
        assertFalse(addFirstToFirstCommand.equals(addFirstToSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index personIndex = INDEX_FIRST;
        Index groupIndex = INDEX_SECOND;
        GroupAddCommand groupAddCommand = new GroupAddCommand(personIndex, groupIndex);

        String expected = GroupAddCommand.class.getCanonicalName() + "{personIndex="
                + personIndex + ", groupIndex=" + groupIndex + "}";
        assertEquals(expected, groupAddCommand.toString());
    }
}
