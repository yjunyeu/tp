package nusconnect.logic.commands;

import static nusconnect.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static nusconnect.logic.Messages.MESSAGE_PERSON_LISTED_OVERVIEW;
import static nusconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nusconnect.testutil.TypicalPersons.ALICE;
import static nusconnect.testutil.TypicalPersons.BENSON;
import static nusconnect.testutil.TypicalPersons.CARL;
import static nusconnect.testutil.TypicalPersons.DANIEL;
import static nusconnect.testutil.TypicalPersons.ELLE;
import static nusconnect.testutil.TypicalPersons.FIONA;
import static nusconnect.testutil.TypicalPersons.GEORGE;
import static nusconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import nusconnect.model.Model;
import nusconnect.model.ModelManager;
import nusconnect.model.UserPrefs;
import nusconnect.model.person.ModuleContainsKeywordsPredicate;
import nusconnect.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        ModuleContainsKeywordsPredicate firstModulePredicate =
                new ModuleContainsKeywordsPredicate(Collections.singletonList("CS2103"));
        ModuleContainsKeywordsPredicate secondModulePredicate =
                new ModuleContainsKeywordsPredicate(Collections.singletonList("CS2040"));

        FindCommand findFirstCommand = new FindCommand(firstNamePredicate, firstModulePredicate);
        FindCommand findSecondCommand = new FindCommand(secondNamePredicate, secondModulePredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstNamePredicate, firstModulePredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicates -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSON_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("");
        ModuleContainsKeywordsPredicate modulePredicate = prepareModulePredicate("");
        FindCommand command = new FindCommand(namePredicate, modulePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(modulePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        ModuleContainsKeywordsPredicate modulePredicate = prepareModulePredicate("");
        FindCommand command = new FindCommand(namePredicate, modulePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(modulePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_moduleKeyword() {
        String expectedMessage = String.format(MESSAGE_PERSON_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("");
        ModuleContainsKeywordsPredicate modulePredicate = prepareModulePredicate("CS2103T");
        FindCommand command = new FindCommand(namePredicate, modulePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(modulePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialNameKeyword() {
        String expectedMessage = String.format(MESSAGE_PERSON_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kur");
        ModuleContainsKeywordsPredicate modulePredicate = prepareModulePredicate("");
        FindCommand command = new FindCommand(namePredicate, modulePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(modulePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialModuleKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("");
        ModuleContainsKeywordsPredicate modulePredicate = prepareModulePredicate("CS2");
        FindCommand command = new FindCommand(namePredicate, modulePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(modulePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameModuleKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kur");
        ModuleContainsKeywordsPredicate modulePredicate = prepareModulePredicate("CS2");
        FindCommand command = new FindCommand(namePredicate, modulePredicate);
        expectedModel.updateFilteredPersonList(namePredicate.or(modulePredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, GEORGE), model.getFilteredPersonList());
    }
    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        ModuleContainsKeywordsPredicate modulePredicate = new ModuleContainsKeywordsPredicate(Arrays.asList("CS2103"));

        FindCommand findCommand = new FindCommand(namePredicate, modulePredicate);

        String expected = FindCommand.class.getCanonicalName() + "{namePredicate=" + namePredicate
                + ", modulePredicate=" + modulePredicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        if (userInput.trim().isEmpty()) {
            return new NameContainsKeywordsPredicate(Collections.emptyList());
        }
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code ModuleContainsKeywordsPredicate}.
     */
    private ModuleContainsKeywordsPredicate prepareModulePredicate(String userInput) {
        if (userInput.trim().isEmpty()) {
            return new ModuleContainsKeywordsPredicate(Collections.emptyList());
        }
        return new ModuleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
