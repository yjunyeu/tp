package nusconnect.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import nusconnect.logic.LogicManager;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;

public class ExportCommandTest {

    private final String testFolder = "src/test/data/JsonAddressBookStorageTest";

    @Test
    public void execute_validFilePath_exportSuccess() throws Exception {
        // Set up mocks
        String validFilePath = testFolder;
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

        // Create the ExportCommand
        ExportCommand exportCommand = new ExportCommand(validFilePath, mockLogicManager);

        // Execute the command
        CommandResult result = exportCommand.execute(mockModel);

        // Verify that the command returns the expected success message
        assertEquals(ExportCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidFilePath_exportFailure() throws Exception {
        // Set up mocks
        String invalidFilePath = "C:/Invalid/Path/addressbook_data.json";
        LogicManager mockLogicManager = mock(LogicManager.class);
        Model mockModel = mock(Model.class);

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
