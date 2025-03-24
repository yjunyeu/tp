package nusconnect.logic.commands;

import java.io.IOException;
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
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports address book data as a JSON file. "
            + "Parameters: FOLDER_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + "C:/Users/User/Documents";

    public static final String MESSAGE_SUCCESS = "Successfully exported address book data.";
    public static final String MESSAGE_FAILURE = "Failed to export address book data.";

    private final Path filePath;
    private final LogicManager logicManager;

    /**
     * @param filePath JSON file path
     */
    public ExportCommand(Path filePath, LogicManager logicManager) {
        this.filePath = filePath;
        this.logicManager = logicManager;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            logicManager.exportAddressBook(filePath);
            return new CommandResult(MESSAGE_SUCCESS);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE + "\nInvalid file path");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand otherExportCommand = (ExportCommand) other;
        return filePath.equals(otherExportCommand.filePath);
    }
}
