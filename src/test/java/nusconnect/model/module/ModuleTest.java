package nusconnect.model.module;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ModuleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidModuleName = "";
        assertThrows(IllegalArgumentException.class, () -> new Module(invalidModuleName));
    }

    @Test
    public void isValidModuleName() {
        // null module name
        assertThrows(NullPointerException.class, () -> Module.isValidModuleName(null));

        // blank module name
        assertFalse(Module.isValidModuleName("")); // empty module name
        assertFalse(Module.isValidModuleName(" ")); // spaces only

        // missing parts
        assertFalse(Module.isValidModuleName("2106")); // missing 1st alphabetic part
        assertFalse(Module.isValidModuleName("2103T")); // missing 1st alphabetic part
        assertFalse(Module.isValidModuleName("GEX")); // missing numeric part

        // invalid parts
        assertFalse(Module.isValidModuleName("C2106")); // 1st alphabetic part below limit
        assertFalse(Module.isValidModuleName("COMP2106")); // 1st alphabetic part above limit
        assertFalse(Module.isValidModuleName("CS2103TE")); // 2nd alphabetic part above limit
        assertFalse(Module.isValidModuleName("CS424")); // numeric part below limit
        assertFalse(Module.isValidModuleName("CS42488")); // numeric part above limit
        assertFalse(Module.isValidModuleName("CS.2103/T")); // illegal characters

        // valid module name
        assertTrue(Module.isValidModuleName("CS4248")); // 2A4N
        assertTrue(Module.isValidModuleName("CS2103T")); // 2A4N1A
        assertTrue(Module.isValidModuleName("GEC1039")); // 3A4N
        assertTrue(Module.isValidModuleName("LAN9988A")); // 3A4N1A
        assertTrue(Module.isValidModuleName("ma2201")); // lowercase characters
        assertTrue(Module.isValidModuleName("gEx1138f")); // lowercase characters
    }

    @Test
    public void equals() {
        Module module = new Module("CS2103T");

        // same values -> returns true
        assertTrue(module.equals(new Module("CS2103T")));

        // same object -> return true
        assertTrue(module.equals(module));

        // same values with different cases -> returns true
        assertTrue(module.equals(new Module("Cs2103t")));

        // null -> returns false
        assertFalse(module.equals(null));

        // different types -> return false
        assertFalse(module.equals(5.0f));

        // different values -> returns false
        assertFalse(module.equals(new Module("GEX1033")));
    }

}
