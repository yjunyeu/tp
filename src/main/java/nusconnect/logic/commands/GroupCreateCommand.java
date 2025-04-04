package nusconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import nusconnect.commons.core.LogsCenter;
import nusconnect.commons.util.ToStringBuilder;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;
import nusconnect.model.group.Group;

/**
 * Creates a new group.
 */
public class GroupCreateCommand extends GroupCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = "group " + COMMAND_WORD
            + ": Creates a new group with the specified name.\n"
            + "Parameters: NAME \n"
            + "Example: group " + COMMAND_WORD + " CS2103T";

    public static final String MESSAGE_SUCCESS = "New group created: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists!";
    private static final Logger logger = LogsCenter.getLogger(GroupCreateCommand.class);

    private final String groupName;

    /**
     * Creates a GroupCreateCommand to add the specified group
     */
    public GroupCreateCommand(String groupName) {
        requireNonNull(groupName);
        assert !groupName.trim().isEmpty() : "Group name must not be empty";
        this.groupName = groupName;
        logger.info("GroupCreateCommand created with groupName: " + groupName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Group groupToAdd = new Group(groupName);

        if (model.hasGroup(groupToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.addGroup(groupToAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupCreateCommand)) {
            return false;
        }

        GroupCreateCommand otherCreateCommand = (GroupCreateCommand) other;
        return groupName.equals(otherCreateCommand.groupName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupName", groupName)
                .toString();
    }
}
