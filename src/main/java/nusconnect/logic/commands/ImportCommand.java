package nusconnect.logic.commands;

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

    private final Path filePath;
    private final LogicManager logicManager;

    /**
     * @param filePath JSON file path
     * @param logicManager used for accessing of storage
     */
    public ImportCommand(Path filePath, LogicManager logicManager) {
        this.filePath = filePath;
        this.logicManager = logicManager;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = logicManager.importAddressBook(filePath);

            if (addressBookOptional.isPresent()) {
                ReadOnlyAddressBook addressBook = addressBookOptional.get();
                model.setAddressBook(addressBook);
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                return new CommandResult(MESSAGE_FAILURE + "\nInvalid FilePath");
            }
        } catch (DataLoadingException e) {
            throw new CommandException(MESSAGE_FAILURE + "\nInvalid JSON file");
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
        return filePath.equals(otherImportCommand.filePath);
    }
}
