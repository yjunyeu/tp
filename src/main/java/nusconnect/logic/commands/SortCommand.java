package nusconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import nusconnect.model.Model;

/**
 * Sorts all the persons in the address book by name in alphabetical order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by name in alphabetical order!";
    public static final String MESSAGE_EMPTY_LIST = "The address book is empty!";
    public static final String MESSAGE_ONE_PERSON = "Sorted one person!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortPersonByName();
        int numOfPersons = model.getFilteredPersonList().size();
        if (numOfPersons == 0) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        } else if (numOfPersons == 1) {
            return new CommandResult(MESSAGE_ONE_PERSON);
        } else {
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
