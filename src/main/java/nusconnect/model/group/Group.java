package nusconnect.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import nusconnect.commons.util.ToStringBuilder;
import nusconnect.model.person.Person;

/**
 * Represents a Group in the address book.
 * Guarantees: immutable
 */
public class Group {

    private final String groupName;
    private final Set<Person> members = new HashSet<>();

    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid group name.
     */
    public Group(String groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
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
