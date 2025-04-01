package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;
import java.nio.file.Paths;

import nusconnect.logic.LogicManager;
import nusconnect.logic.commands.ExportCommand;
import nusconnect.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    private final LogicManager logicManager;

    public ExportCommandParser(LogicManager logicManager) {
        this.logicManager = logicManager;
    }


    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().split("\\.")[0];
        Path path = Paths.get(trimmedArgs);

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        trimmedArgs += ".json";
        Path filePath = Path.of(trimmedArgs);
        return new ExportCommand(filePath, logicManager);
    }
}

