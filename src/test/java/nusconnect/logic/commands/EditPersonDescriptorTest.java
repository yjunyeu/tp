package nusconnect.logic.commands;

import static nusconnect.logic.commands.CommandTestUtil.DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_ALIAS_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MODULE_CS2103T;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nusconnect.logic.commands.EditCommand.EditPersonDescriptor;
import nusconnect.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different alias -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAlias(VALID_ALIAS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different major -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withMajor(VALID_MAJOR_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different note -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withNote(VALID_NOTE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different telegram -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTelegram(VALID_TELEGRAM_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different website -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withWebsite(VALID_WEBSITE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different Modules -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withModules(VALID_MODULE_CS2103T).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        String expected = EditPersonDescriptor.class.getCanonicalName() + "{name="
                + editPersonDescriptor.getNote() + ", telegram="
                + editPersonDescriptor.getName() + ", phone="
                + editPersonDescriptor.getPhone() + ", email="
                + editPersonDescriptor.getEmail() + ", alias="
                + editPersonDescriptor.getAlias() + ", major="
                + editPersonDescriptor.getMajor() + ", note="
                + editPersonDescriptor.getTelegram() + ", website="
                + editPersonDescriptor.getWebsite() + ", modules="
                + editPersonDescriptor.getModules().orElse(null) + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }
}
