package nusconnect.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nusconnect.commons.exceptions.IllegalValueException;
import nusconnect.model.group.Group;
import nusconnect.model.person.Person;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String groupName;
    private final List<JsonAdaptedPerson> members = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given group details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("groupName") String groupName,
                            @JsonProperty("members") List<JsonAdaptedPerson> members) {
        this.groupName = groupName;
        if (members != null) {
            this.members.addAll(members);
        }
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        groupName = source.getGroupName();
        members.addAll(source.getMembers().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted group object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group.
     */
    public Group toModelType() throws IllegalValueException {
        if (groupName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "group name"));
        }
        if (!Group.isValidGroupName(groupName)) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }

        Group group = new Group(groupName);

        for (JsonAdaptedPerson jsonAdaptedPerson : members) {
            Person person = jsonAdaptedPerson.toModelType();
            group.addMember(person);
        }

        return group;
    }
}
