package nusconnect.logic.commands;

import java.io.IOError;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;

import nusconnect.commons.exceptions.DataLoadingException;
import nusconnect.logic.LogicManager;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;
import nusconnect.model.ReadOnlyAddressBook;

/**
 * Imports a new JSON file consisting of new addressbook
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports address book data from a JSON file. "
            + "Parameters: FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + "C:/Users/User/Documents/addressbook_data.json";

    public static final String MESSAGE_SUCCESS = "Successfully imported address book data.";
    public static final String MESSAGE_FAILURE = "Failed to import address book data.";

    private final String fileString;
    private final LogicManager logicManager;

    /**
     * @param fileString JSON file path
     * @param logicManager used for accessing of storage
     */
    public ImportCommand(String fileString, LogicManager logicManager) {
        this.fileString = fileString;
        this.logicManager = logicManager;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            Path filePath = Path.of(fileString);
            Optional<ReadOnlyAddressBook> addressBookOptional = logicManager.importAddressBook(filePath);

            if (addressBookOptional.isPresent()) {
                ReadOnlyAddressBook addressBook = addressBookOptional.get();
                model.setAddressBook(addressBook);
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                throw new CommandException(MESSAGE_FAILURE + "\nInvalid file path!");
            }
        } catch (DataLoadingException e) {
            throw new CommandException(MESSAGE_FAILURE + "\nInvalid JSON file!");
        } catch (InvalidPathException | IOError e) {
            throw new CommandException(MESSAGE_FAILURE + "\nInvalid file path!");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return fileString.equals(otherImportCommand.fileString);
    }
}
