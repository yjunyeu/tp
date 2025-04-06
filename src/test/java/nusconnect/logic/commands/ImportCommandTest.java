package nusconnect.logic.commands;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import nusconnect.commons.exceptions.DataLoadingException;
import nusconnect.logic.LogicManager;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;
import nusconnect.model.ReadOnlyAddressBook;

public class ImportCommandTest {

    @Test
    public void execute_validFile_importSuccess() throws Exception {
        // Set up mocks
        ReadOnlyAddressBook mockAddressBook = mock(ReadOnlyAddressBook.class);
        String validFileString = "C:/tp/src/test/data/JsonAddressBookStorageTest/validPersonAddressBook.json";
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

        when(mockLogicManager.importAddressBook(Path.of(validFileString)))
                .thenReturn(Optional.of(mockAddressBook));
        // Create the ImportCommand
        ImportCommand importCommand = new ImportCommand(validFileString, mockLogicManager);

        // Execute and check the result
        CommandResult result = importCommand.execute(mockModel);
        assertEquals(ImportCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

    }

    @Test
    public void execute_invalidFilePath_importFailure() throws Exception {
        // Set up mocks
        ReadOnlyAddressBook mockAddressBook = mock(ReadOnlyAddressBook.class);
        String invalidFileString = "src/test/data/invalidPersonAddressBook.json";
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

        when(mockLogicManager.importAddressBook(Path.of(invalidFileString)))
                .thenThrow(InvalidPathException.class);

        // Create the ImportCommand
        ImportCommand importCommand = new ImportCommand(invalidFileString, mockLogicManager);

        assertThrows(CommandException.class, () -> importCommand.execute(mockModel));
    }

    @Test
    public void execute_dataLoadingException_importFailure() throws Exception {
        // Set up mocks
        ReadOnlyAddressBook mockAddressBook = mock(ReadOnlyAddressBook.class);

        String invalidJsonFileString = "C:/tp/src/test/data/JsonAddressBookStorageTest/notJsonFormatAddressBook.json";
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

        when(mockLogicManager.importAddressBook(Path.of(invalidJsonFileString)))
                .thenThrow(DataLoadingException.class);

        // Create the ImportCommand
        ImportCommand importCommand = new ImportCommand(invalidJsonFileString, mockLogicManager);

        // Execute and check the result
        assertThrows(CommandException.class, () -> importCommand.execute(mockModel));
    }


}
