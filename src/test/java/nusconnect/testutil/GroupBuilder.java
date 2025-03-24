package nusconnect.testutil;

import java.util.HashSet;
import java.util.Set;

import nusconnect.model.group.Group;
import nusconnect.model.person.Person;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_GROUP_NAME = "CS2103T";

    private String groupName;
    private Set<Person> members;

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        groupName = DEFAULT_GROUP_NAME;
        members = new HashSet<>();
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        groupName = groupToCopy.getGroupName();
        members = new HashSet<>(groupToCopy.getMembers());
    }

    /**
     * Sets the {@code groupName} of the {@code Group} that we are building.
     */
    public GroupBuilder withName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    /**
     * Adds a {@code person} to the members of the {@code Group} that we are building.
     */
    public GroupBuilder withMember(Person person) {
        this.members.add(person);
        return this;
    }

    /**
     * Builds a Group with the specified members.
     */
    public Group build() {
        Group group = new Group(groupName);
        for (Person member : members) {
            group.addMember(member);
        }
        return group;
    }
}
