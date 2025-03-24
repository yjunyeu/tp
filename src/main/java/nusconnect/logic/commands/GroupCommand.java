package nusconnect.logic.commands;

/**
 * Represents a command with operations related to Groups.
 */
public abstract class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Performs operations related to groups.\n"
            + "Subcommands: create, add, delete\n"
            + "Example: " + COMMAND_WORD + " create CS2103T";
}
