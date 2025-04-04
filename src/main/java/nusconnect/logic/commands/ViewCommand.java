package nusconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import nusconnect.commons.core.LogsCenter;
import nusconnect.commons.core.index.Index;
import nusconnect.commons.util.ToStringBuilder;
import nusconnect.logic.Messages;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;
import nusconnect.model.person.Person;

/**
 * Shows the details of the specified person in the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the details of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Viewing Person: %1$s";

    private static final Logger logger = LogsCenter.getLogger(ViewCommand.class);
    private final Index targetIndex;

    /**
     * Creates a ViewCommand to see the detail of the specified person
     *
     * @param targetIndex index of the person in the filtered person list
     */
    public ViewCommand(Index targetIndex) {
        assert targetIndex.getOneBased() > 0 : "Target index must be positive";
        this.targetIndex = targetIndex;
        logger.info("ViewCommand created with target index: " + targetIndex.getOneBased());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        assert lastShownList != null : "Filtered person list should not be null";

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(targetIndex.getZeroBased());

        return new CommandResult(
                String.format(MESSAGE_VIEW_PERSON_SUCCESS, Messages.format(personToView)),
                targetIndex);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return targetIndex.equals(otherViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
