package nusconnect.logic.commands;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import nusconnect.commons.exceptions.DataLoadingException;
import nusconnect.logic.LogicManager;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;
import nusconnect.model.ReadOnlyAddressBook;

public class ImportCommandTest {

    @TempDir
    public Path testFolder = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @Test
    public void execute_validFile_importSuccess() throws Exception {
        // Set up mocks
        Path validFilePath = testFolder.resolve("validPersonAddressBook.json");
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

        // Mock the behavior of importAddressBook to return a valid address book
        ReadOnlyAddressBook mockAddressBook = mock(ReadOnlyAddressBook.class);
        when(mockLogicManager.importAddressBook(validFilePath)).thenReturn(Optional.of(mockAddressBook));

        // Create the ImportCommand
        ImportCommand importCommand = new ImportCommand(validFilePath, mockLogicManager);

        // Execute and check the result
        CommandResult result = importCommand.execute(mockModel);
        assertEquals(ImportCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Verify interactions
        verify(mockLogicManager).importAddressBook(validFilePath);
        verify(mockModel).setAddressBook(mockAddressBook);
    }

    @Test
    public void execute_invalidFilePath_importFailure() throws Exception {
        // Set up mocks
        Path invalidFilePath = testFolder.resolve("invalidPersonAddressBook.json");
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

        // Mock the behavior of importAddressBook to return an empty Optional (file not found)
        when(mockLogicManager.importAddressBook(invalidFilePath)).thenReturn(Optional.empty());

        // Create the ImportCommand
        ImportCommand importCommand = new ImportCommand(invalidFilePath, mockLogicManager);

        // Execute and check the result
        CommandResult result = importCommand.execute(mockModel);
        assertEquals(ImportCommand.MESSAGE_FAILURE + "\nInvalid FilePath", result.getFeedbackToUser());

        // Verify interactions
        verify(mockLogicManager).importAddressBook(invalidFilePath);
        verifyNoInteractions(mockModel); // No address book set in case of failure
    }

    @Test
    public void execute_dataLoadingException_importFailure() throws Exception {
        // Set up mocks
        Path invalidJsonFilePath = testFolder.resolve("notJsonFormatAddressBook.json");
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

        // Mock the behavior of importAddressBook to throw a DataLoadingException
        when(mockLogicManager.importAddressBook(invalidJsonFilePath)).thenThrow(DataLoadingException.class);

        // Create the ImportCommand
        ImportCommand importCommand = new ImportCommand(invalidJsonFilePath, mockLogicManager);

        // Execute and check the result
        assertThrows(CommandException.class, () -> importCommand.execute(mockModel));
    }


}
