package nusconnect.model.module;

import static nusconnect.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Module(invalidTagName));
    }

    @Test
    public void isValidModuleName() {
        // null module name
        assertThrows(NullPointerException.class, () -> Module.isValidModuleName(null));
    }

}
