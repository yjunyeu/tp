package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import nusconnect.logic.commands.FindCommand;
import nusconnect.model.person.ModuleContainsKeywordsPredicate;
import nusconnect.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Example: Keywords for both name and module are provided
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "CS2103T")),
                        new ModuleContainsKeywordsPredicate(Arrays.asList("Alice", "Bob", "CS2103T")));
        assertParseSuccess(parser, "Alice Bob CS2103T", expectedFindCommand);

        // Test with leading and trailing whitespaces
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t CS2103T", expectedFindCommand);

        // Example: Keywords for name only, module predicate is empty
        expectedFindCommand = new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                new ModuleContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
    }

}
