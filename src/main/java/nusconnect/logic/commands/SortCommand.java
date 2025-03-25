package nusconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import nusconnect.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by name in alphabetical order";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortPersonByName();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
