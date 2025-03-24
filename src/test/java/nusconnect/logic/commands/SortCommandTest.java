package nusconnect.logic.commands;

import static nusconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nusconnect.model.AddressBook;
import nusconnect.model.Model;
import nusconnect.model.ModelManager;
import nusconnect.model.UserPrefs;
import nusconnect.model.person.Person;
import nusconnect.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for sorting functionality.
 */
public class SortCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortPersonsByName_sortedSuccessfully() {
        model.sortPersonByName();
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person secondPerson = model.getFilteredPersonList().get(1);
        assertTrue(firstPerson.getName().toString().compareTo(secondPerson.getName().toString()) < 0);
    }

    @Test
    public void execute_sortAlreadySortedList_sortedSuccessfully() {
        model.sortPersonByName();
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person secondPerson = model.getFilteredPersonList().get(1);
        assertTrue(firstPerson.getName().toString().compareTo(secondPerson.getName().toString()) < 0);
    }

    @Test
    public void execute_sortEmptyList_sortedSuccessfully() {
        model.setAddressBook(new AddressBook());
        model.sortPersonByName();
        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_sortAfterAddingPerson_sortedSuccessfully() {
        model.sortPersonByName();
        Person newPerson = new PersonBuilder().withName("Zara").build();
        model.addPerson(newPerson);
        model.sortPersonByName();
        Person lastPerson = model.getFilteredPersonList().get(model.getFilteredPersonList().size() - 1);
        assertEquals("Zara", lastPerson.getName().toString());
    }
}
