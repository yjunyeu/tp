package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.ParserUtil.parseIndex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nusconnect.commons.core.index.Index;
import nusconnect.logic.commands.GroupAddCommand;
import nusconnect.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GroupAddCommand object
 */
public class GroupAddCommandParser implements Parser<GroupAddCommand> {

    private static final Pattern ADD_COMMAND_FORMAT =
            Pattern.compile("(?<personIndex>\\d+)\\s+to\\s+(?<groupIndex>\\d+)");

    /**
     * Parses the given {@code String} of arguments in the context of the GroupAddCommand
     * and returns a GroupAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupAddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        Matcher matcher = ADD_COMMAND_FORMAT.matcher(trimmedArgs);

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
        }

        try {
            Index personIndex = parseIndex(matcher.group("personIndex"));
            Index groupIndex = parseIndex(matcher.group("groupIndex"));
            return new GroupAddCommand(personIndex, groupIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE), pe);
        }
    }
}
