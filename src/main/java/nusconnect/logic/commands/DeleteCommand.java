package nusconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import nusconnect.commons.core.index.Index;
import nusconnect.commons.util.ToStringBuilder;
import nusconnect.logic.Messages;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;
import nusconnect.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or INDICES separated by single spaces\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_MULTIPLE_PERSONS_SUCCESS = "Deleted multiple persons.\n";

    private final List<Index> targetIndices;

    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    private String deletePerson(Model model, Person personToDelete) {
        model.deletePerson(personToDelete);
        return String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        int maxIndex = lastShownList.size();
        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= maxIndex) {
                throw new CommandException(
                        String.format(Messages.MESSAGE_INVALID_PERSON_TO_DELETE_INDEX, targetIndex.getOneBased()));
            }
        }
        String result = targetIndices.stream()
                .map(targetIndex -> {
                    Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
                    return deletePerson(model, personToDelete);
                })
                .collect(Collectors.joining("\n"));
        if (targetIndices.size() != 1) {
            result = MESSAGE_DELETE_MULTIPLE_PERSONS_SUCCESS + result;
        }
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndices)
                .toString();
    }
}
