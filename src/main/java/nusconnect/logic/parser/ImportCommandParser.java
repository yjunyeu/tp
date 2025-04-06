package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import nusconnect.logic.LogicManager;
import nusconnect.logic.commands.ImportCommand;
import nusconnect.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private final LogicManager logicManager;

    public ImportCommandParser(LogicManager logicManager) {
        this.logicManager = logicManager;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        return new ImportCommand(trimmedArgs, logicManager);
    }
}

