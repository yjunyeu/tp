package nusconnect.model.group;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import nusconnect.model.group.exceptions.DuplicateGroupException;

public class UniqueGroupListTest {

    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.contains(null));
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupList.contains(new Group("CS2103T")));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupList.add(new Group("CS2103T"));
        assertTrue(uniqueGroupList.contains(new Group("CS2103T")));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.add(null));
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateGroupException() {
        uniqueGroupList.add(new Group("CS2103T"));
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.add(new Group("CS2103T")));
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroups((List<Group>) null));
    }

    @Test
    public void setGroups_uniqueGroupList_replacesOwnListWithProvidedUniqueGroupList() {
        uniqueGroupList.add(new Group("CS2103T"));
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(new Group("CS2106"));
        uniqueGroupList.setGroups(expectedUniqueGroupList.asUnmodifiableObservableList());
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroups((List<Group>) null));
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupList.add(new Group("CS2103T"));
        List<Group> groupList = Collections.singletonList(new Group("CS2106"));
        uniqueGroupList.setGroups(groupList);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(new Group("CS2106"));
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Group> listWithDuplicateGroups = Arrays.asList(new Group("CS2103T"), new Group("CS2103T"));
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.setGroups(listWithDuplicateGroups));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueGroupList.asUnmodifiableObservableList().remove(0));
    }
}
