package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;
import static nusconnect.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import nusconnect.logic.commands.GroupAddCommand;

public class GroupAddCommandParserTest {

    private GroupAddCommandParser parser = new GroupAddCommandParser();

    @Test
    public void parse_validArgs_returnsGroupAddCommand() {

        assertParseSuccess(parser, "1 to 2", new GroupAddCommand(INDEX_FIRST, INDEX_SECOND));

        assertParseSuccess(parser, "2 to 1", new GroupAddCommand(INDEX_SECOND, INDEX_FIRST));

        assertParseSuccess(parser, " 1  to  2 ", new GroupAddCommand(INDEX_FIRST, INDEX_SECOND));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "a to b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "a to 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 to b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "to",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "to 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 to",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 to 2 extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
    }
}
