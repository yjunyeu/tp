package nusconnect.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import nusconnect.logic.LogicManager;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;

public class ExportCommandTest {

    private final Path testFolder = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @Test
    public void execute_validFilePath_exportSuccess() throws Exception {
        // Set up mocks
        Path validFilePath = testFolder;
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

        // Create the ExportCommand
        ExportCommand exportCommand = new ExportCommand(validFilePath, mockLogicManager);

        // Execute the command
        CommandResult result = exportCommand.execute(mockModel);

        // Verify that the command returns the expected success message
        assertEquals(ExportCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Verify that the exportAddressBook method was called with the correct file path
        verify(mockLogicManager).exportAddressBook(validFilePath);
    }

    @Test
    public void execute_invalidFilePath_exportFailure() throws Exception {
        // Set up mocks
        Path invalidFilePath = Paths.get("C:/Invalid/Path/addressbook_data.json");
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

        // Mock the exportAddressBook method to throw IOException
        doThrow(IOException.class).when(mockLogicManager).exportAddressBook(invalidFilePath);

        // Create the ExportCommand
        ExportCommand exportCommand = new ExportCommand(invalidFilePath, mockLogicManager);

        // Execute the command and check for CommandException
        CommandException exception = null;
        try {
            exportCommand.execute(mockModel);
        } catch (CommandException e) {
            exception = e;
        }

        // Verify that the exception was thrown and contains the correct failure message
        assertEquals(ExportCommand.MESSAGE_FAILURE + "\nInvalid file path!", exception.getMessage());

    }


}
