package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import nusconnect.logic.commands.GroupCreateCommand;
import nusconnect.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GroupCreateCommand object
 */
public class GroupCreateCommandParser implements Parser<GroupCreateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupCreateCommand
     * and returns a GroupCreateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCreateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCreateCommand.MESSAGE_USAGE));
        }

        return new GroupCreateCommand(trimmedArgs);
    }
}
