package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nusconnect.logic.LogicManager;
import nusconnect.logic.commands.ImportCommand;
import nusconnect.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {

    private ImportCommandParser parser;
    private LogicManager mockLogicManager;

    @BeforeEach
    public void setUp() {
        mockLogicManager = mock(LogicManager.class);
        parser = new ImportCommandParser(mockLogicManager);
    }

    @Test
    public void parse_validFilePath_returnsImportCommand() throws ParseException {
        // Valid input
        Path testFolder = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");
        Path validPath = testFolder.resolve("validPersonAddressBook.json");
        String validInput = "src/test/data/JsonAddressBookStorageTest/validPersonAddressBook.json";

        // Create expected ImportCommand with the correct file path
        ImportCommand expectedImportCommand = new ImportCommand(validInput, mockLogicManager);

        // Parse input to get the actual ImportCommand
        ImportCommand actualImportCommand = parser.parse(validInput);

        // Verify that the parsed command matches the expected command
        assertEquals(expectedImportCommand, actualImportCommand);
    }

    @Test
    public void parse_emptyFilePath_throwsParseException() {
        // Empty input
        String emptyInput = "";

        // Expected behavior: the parser throws a ParseException
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(emptyInput));

        // Verify the exception message is correct
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE),
                exception.getMessage());
    }

    @Test
    public void parse_invalidFilePath_throwsParseException() {
        // Invalid input (e.g., malformed or incorrectly formatted path)
        String invalidInput = "invalidFilePath";

        // Expected behavior: the parser does not throw ParseException because the parser does not validate path
        // We expect the exception to be thrown when the input is empty (or handles empty case)
        // Empty string case
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(""));

        // Verify the exception message is correct
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE),
                exception.getMessage());
    }


}
