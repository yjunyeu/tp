package nusconnect.model.group;

import static java.util.Objects.requireNonNull;
import static nusconnect.commons.util.AppUtil.checkArgument;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import nusconnect.commons.util.ToStringBuilder;
import nusconnect.model.person.Person;

/**
 * Represents a Group in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGroupName(String)}
 */
public class Group {

    public static final String MESSAGE_CONSTRAINTS = "Group names should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String groupName;
    private final Set<Person> members = new HashSet<>();

    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid group name.
     */
    public Group(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
    }

    /**
     * Returns true if a given string is a valid group name.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the name of the group.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Returns an immutable set of members in this group.
     */
    public Set<Person> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    /**
     * Returns true if this group contains the specified person.
     */
    public boolean hasMember(Person person) {
        requireNonNull(person);
        return members.contains(person);
    }

    /**
     * Adds a member to this group.
     */
    public void addMember(Person person) {
        requireNonNull(person);
        members.add(person);
    }

    /**
     * Removes a member from this group.
     */
    public void removeMember(Person person) {
        requireNonNull(person);
        members.remove(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return groupName.equals(otherGroup.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupName", groupName)
                .add("memberCount", members.size())
                .toString();
    }
}
