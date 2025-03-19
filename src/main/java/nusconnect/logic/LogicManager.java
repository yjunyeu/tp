package nusconnect.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import nusconnect.commons.core.GuiSettings;
import nusconnect.commons.core.LogsCenter;
import nusconnect.commons.exceptions.DataLoadingException;
import nusconnect.logic.commands.Command;
import nusconnect.logic.commands.CommandResult;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.logic.parser.AddressBookParser;
import nusconnect.logic.parser.exceptions.ParseException;
import nusconnect.model.Model;
import nusconnect.model.ReadOnlyAddressBook;
import nusconnect.model.person.Person;
import nusconnect.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    public void importAddressBook(Path filepath) {
        logger.info("Attempting to import address book from: " + filepath);
        Optional<ReadOnlyAddressBook> addressBookOptional;
        try {
            addressBookOptional = storage.readAddressBook(filepath);
            if (addressBookOptional.isPresent()) {
                model.setAddressBook(addressBookOptional.get());
                logger.info("Address book successfully imported from: " + filepath);
            } else {
                logger.warning("No address book found at " + filepath + ". Import ignored.");
            }

        } catch (DataLoadingException e) {
            logger.warning("Error importing address book from " + filepath + ". " + e.getMessage() + ". Import ignored.");
        }
    }

    public void exportAddressBook(Path filepath) {
        logger.info("Attempting to export address book to: " + filepath);

        try {
                storage.saveAddressBook(model.getAddressBook(), filepath);
            logger.info("Address book successfully exported to: " + filepath);
        } catch (IOException e) {
            logger.severe("Error exporting address book: " + e.getMessage());
        }
    }

}
