package nusconnect.logic.commands;

import static nusconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nusconnect.logic.commands.CommandTestUtil.showPersonAtIndex;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;
import static nusconnect.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nusconnect.model.AddressBook;
import nusconnect.model.Model;
import nusconnect.model.ModelManager;
import nusconnect.model.UserPrefs;
import nusconnect.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyList_showsEmptyMessage() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        model.updateFilteredPersonList(person->false);
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_EMPTY_LIST, expectedModel);
    }

    @Test
    public void execute_onePersonInList_showsOnePersonMessage() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(new PersonBuilder().build());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_ONE_PERSON, expectedModel);
    }
}
