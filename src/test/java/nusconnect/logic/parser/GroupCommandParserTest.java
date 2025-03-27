package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import nusconnect.logic.commands.GroupAddCommand;
import nusconnect.logic.commands.GroupCommand;
import nusconnect.logic.commands.GroupCreateCommand;
import nusconnect.logic.commands.GroupDeleteCommand;

public class GroupCommandParserTest {

    private final GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSubCommand_throwsParseException() {
        assertParseFailure(parser, "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Unknown group subcommand: invalid"));
    }

    @Test
    public void parse_createSubCommand_success() {
        String groupName = "CS2103T";
        assertParseSuccess(parser, "create " + groupName, new GroupCreateCommand(groupName));
    }

    @Test
    public void parse_deleteSubCommand_success() {
        assertParseSuccess(parser, "delete 1", new GroupDeleteCommand(INDEX_FIRST));
    }

    @Test
    public void parse_addSubCommand_success() {
        assertParseSuccess(parser, "add 1 to 1", new GroupAddCommand(INDEX_FIRST, INDEX_FIRST));
    }
}
