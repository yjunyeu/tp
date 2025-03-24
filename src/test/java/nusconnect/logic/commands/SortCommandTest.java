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
        // Call the sorting function
        model.sortPersonByName();

        // Get the first and second persons after sorting
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person secondPerson = model.getFilteredPersonList().get(1);

        // Assert that AMY comes before BOB alphabetically
        assertTrue(firstPerson.getName().toString().compareTo(secondPerson.getName().toString()) < 0);
    }

    @Test
    public void execute_sortAlreadySortedList_sortedSuccessfully() {
        // Setup a sorted list
        model.sortPersonByName();
        Person firstPerson = model.getFilteredPersonList().get(0);
        Person secondPerson = model.getFilteredPersonList().get(1);

        // Assert that the first person comes before the second
        assertTrue(firstPerson.getName().toString().compareTo(secondPerson.getName().toString()) < 0);
    }

    @Test
    public void execute_sortEmptyList_sortedSuccessfully() {
        // Clear the list before testing
        model.setAddressBook(new AddressBook());

        // Call sort function
        model.sortPersonByName();

        // Assert that the list is still empty
        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_sortAfterAddingPerson_sortedSuccessfully() {
        // Initial sorting
        model.sortPersonByName();

        // Add a new person
        Person newPerson = new PersonBuilder().withName("Zara").build();
        model.addPerson(newPerson);

        // Call the sort function again
        model.sortPersonByName();

        // Check the position of the new person
        Person lastPerson = model.getFilteredPersonList().get(model.getFilteredPersonList().size() - 1);

        // Assert that Zara is now at the end of the list
        assertEquals("Zara", lastPerson.getName().toString());
    }
}
