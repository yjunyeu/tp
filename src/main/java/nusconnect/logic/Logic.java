package nusconnect.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import nusconnect.commons.core.GuiSettings;
import nusconnect.commons.exceptions.DataLoadingException;
import nusconnect.logic.commands.CommandResult;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.logic.parser.exceptions.ParseException;
import nusconnect.model.Model;
import nusconnect.model.ReadOnlyAddressBook;
import nusconnect.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Imports addressbook
     */
    Optional<ReadOnlyAddressBook> importAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Exports addressbook
     */
    void exportAddressBook(Path filePath) throws IOException;
}
