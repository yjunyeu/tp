package nusconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import nusconnect.commons.core.LogsCenter;
import nusconnect.commons.core.index.Index;
import nusconnect.commons.util.ToStringBuilder;
import nusconnect.logic.Messages;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;
import nusconnect.model.group.Group;
import nusconnect.model.person.Person;

/**
 * Adds a person to a group identified by their displayed indices.
 */
public class GroupAddCommand extends GroupCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = GroupCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a person to a group.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) to GROUP_INDEX (must be a positive integer)\n"
            + "Example: group " + COMMAND_WORD + " 1 to 2";

    public static final String MESSAGE_SUCCESS = "Added person %1$s to group %2$s";
    public static final String MESSAGE_PERSON_ALREADY_IN_GROUP = "This person is already in the group";

    private final Index personIndex;
    private final Index groupIndex;

    /**
     * Creates a GroupAddCommand to add the specified person to the specified group
     *
     * @param personIndex index of the person in the filtered person list
     * @param groupIndex index of the group in the filtered group list
     */
    public GroupAddCommand(Index personIndex, Index groupIndex) {
        requireNonNull(personIndex);
        requireNonNull(groupIndex);
        this.personIndex = personIndex;
        this.groupIndex = groupIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (personIndex.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (groupIndex.getZeroBased() >= model.getFilteredGroupList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_INDEX);
        }

        Person personToAdd = model.getFilteredPersonList().get(personIndex.getZeroBased());
        Group targetGroup = model.getFilteredGroupList().get(groupIndex.getZeroBased());

        if (targetGroup.hasMember(personToAdd)) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_IN_GROUP);
        }

        model.addPersonToGroup(personToAdd, targetGroup);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                personToAdd.getName().fullName, targetGroup.getGroupName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupAddCommand)) {
            return false;
        }

        GroupAddCommand otherAddCommand = (GroupAddCommand) other;
        return personIndex.equals(otherAddCommand.personIndex)
                && groupIndex.equals(otherAddCommand.groupIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("groupIndex", groupIndex)
                .toString();
    }
}
