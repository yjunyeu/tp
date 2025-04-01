package nusconnect.logic.parser;

import static nusconnect.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static nusconnect.testutil.Assert.assertThrows;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import nusconnect.logic.parser.exceptions.ParseException;
import nusconnect.model.module.Module;

public class ParserUtilTest {
    private static final String INVALID_MODULE = "#CS2103T";

    private static final String VALID_MODULE_1 = "CS2106";
    private static final String VALID_MODULE_2 = "CS2103T";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseModule_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModule(null));
    }

    @Test
    public void parseModule_validValueWithoutWhitespace_returnsModule() throws Exception {
        Module expectedModule = new Module(VALID_MODULE_1);
        assertEquals(expectedModule, ParserUtil.parseModule(VALID_MODULE_1));
    }

    @Test
    public void parseModule_invalidValueWithoutWhitespace_stillReturnsModule() throws Exception {
        Module expectedModule = new Module(INVALID_MODULE);
        assertEquals(expectedModule, ParserUtil.parseModule(INVALID_MODULE));
    }

    @Test
    public void parseModule_validValueWithWhitespace_returnsTrimmedModule() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_MODULE_1 + WHITESPACE;
        Module expectedModule = new Module(VALID_MODULE_1);
        assertEquals(expectedModule, ParserUtil.parseModule(tagWithWhitespace));
    }

    @Test
    public void parseModules_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModules(null));
    }

    @Test
    public void parseModules_collectionWithInvalidModules_stillReturnsModuleSet() throws Exception {
        Set<Module> actualModuleSet = ParserUtil.parseModules(Arrays.asList(VALID_MODULE_1, INVALID_MODULE));
        Set<Module> expectedModuleSet = new HashSet<Module>(Arrays.asList(new Module(VALID_MODULE_1),
                new Module(INVALID_MODULE)));

        assertEquals(expectedModuleSet, actualModuleSet);
    }

    @Test
    public void parseModules_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseModules(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseModules_collectionWithValidModules_returnsModuleSet() throws Exception {
        Set<Module> actualModuleSet = ParserUtil.parseModules(Arrays.asList(VALID_MODULE_1, VALID_MODULE_2));
        Set<Module> expectedModuleSet = new HashSet<Module>(Arrays.asList(new Module(VALID_MODULE_1),
                new Module(VALID_MODULE_2)));

        assertEquals(expectedModuleSet, actualModuleSet);
    }
}
