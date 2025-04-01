package nusconnect.logic.commands;

import static java.util.Objects.requireNonNull;
import static nusconnect.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import nusconnect.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_EMPTY_LIST = "The address book is empty.";
    public static final String MESSAGE_ONE_PERSON = "Listed one person.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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
