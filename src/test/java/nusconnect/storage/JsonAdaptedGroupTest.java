package nusconnect.storage;

import static nusconnect.storage.JsonAdaptedGroup.MISSING_FIELD_MESSAGE_FORMAT;
import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nusconnect.commons.exceptions.IllegalValueException;
import nusconnect.model.group.Group;
import nusconnect.model.person.Person;
import nusconnect.testutil.GroupBuilder;
import nusconnect.testutil.PersonBuilder;
import nusconnect.testutil.TypicalPersons;

public class JsonAdaptedGroupTest {

    private static final String INVALID_GROUP_NAME = "@CS2103T";
    private static final String VALID_GROUP_NAME = "CS2103T";
    private static final List<JsonAdaptedPerson> VALID_MEMBERS = TypicalPersons.getTypicalPersons().stream()
            .map(JsonAdaptedPerson::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedPerson> EMPTY_MEMBERS = new ArrayList<>();

    @Test
    public void toModelType_validGroupDetails_returnsGroup() throws Exception {
        Person person = new PersonBuilder().build();
        Group originalGroup = new GroupBuilder().withName(VALID_GROUP_NAME).withMember(person).build();
        JsonAdaptedGroup jsonGroup = new JsonAdaptedGroup(originalGroup);

        Group modelGroup = jsonGroup.toModelType();
        assertEquals(VALID_GROUP_NAME, modelGroup.getGroupName());
        assertEquals(1, modelGroup.getMembers().size());
    }

    @Test
    public void toModelType_validGroupNoMembers_returnsGroup() throws Exception {
        JsonAdaptedGroup jsonGroup = new JsonAdaptedGroup(VALID_GROUP_NAME, EMPTY_MEMBERS);
        Group modelGroup = jsonGroup.toModelType();
        assertEquals(VALID_GROUP_NAME, modelGroup.getGroupName());
        assertEquals(0, modelGroup.getMembers().size());
    }

    @Test
    public void toModelType_invalidGroupName_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(INVALID_GROUP_NAME, VALID_MEMBERS);
        String expectedMessage = Group.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullGroupName_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(null, VALID_MEMBERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "group name");
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }
}
