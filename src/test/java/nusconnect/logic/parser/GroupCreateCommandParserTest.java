package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import nusconnect.logic.commands.GroupCreateCommand;

public class GroupCreateCommandParserTest {

    private GroupCreateCommandParser parser = new GroupCreateCommandParser();

    @Test
    public void parse_validArgs_returnsGroupCreateCommand() {
        assertParseSuccess(parser, "CS2103T", new GroupCreateCommand("CS2103T"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCreateCommand.MESSAGE_USAGE));
    }
}
