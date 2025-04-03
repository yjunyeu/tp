package nusconnect.storage;

import static nusconnect.logic.parser.CliSyntax.PREFIX_ALIAS;
import static nusconnect.logic.parser.CliSyntax.PREFIX_COURSE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_NOTE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static nusconnect.logic.parser.CliSyntax.PREFIX_WEBSITE;
import static nusconnect.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static nusconnect.testutil.Assert.assertThrows;
import static nusconnect.testutil.TypicalPersons.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nusconnect.commons.exceptions.IllegalValueException;
import nusconnect.model.person.Alias;
import nusconnect.model.person.Course;
import nusconnect.model.person.Email;
import nusconnect.model.person.Name;
import nusconnect.model.person.Note;
import nusconnect.model.person.Phone;
import nusconnect.model.person.Telegram;
import nusconnect.model.person.Website;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ALIAS = " " + PREFIX_ALIAS;
    private static final String INVALID_COURSE = " " + PREFIX_COURSE;
    private static final String INVALID_NOTE = " " + PREFIX_NOTE;
    private static final String INVALID_TELEGRAM = " " + PREFIX_TELEGRAM;
    private static final String INVALID_WEBSITE = " " + PREFIX_WEBSITE;

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_TELEGRAM = BENSON.getTelegram().toString();
    private static final String VALID_PHONE = BENSON.getPhone().map(Phone::toString).orElse(null);
    private static final String VALID_EMAIL = BENSON.getEmail().map(Email::toString).orElse(null);
    private static final String VALID_ALIAS = BENSON.getAlias().map(Alias::toString).orElse(null);
    private static final String VALID_COURSE = BENSON.getCourse().map(Course::toString).orElse(null);
    private static final String VALID_NOTE = BENSON.getNote().map(Note::toString).orElse(null);
    private static final String VALID_WEBSITE = BENSON.getWebsite().map(Website::toString).orElse(null);

    private static final List<JsonAdaptedModule> VALID_TAGS = BENSON.getModules().stream()
            .map(JsonAdaptedModule::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_TELEGRAM,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_COURSE, VALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_TELEGRAM,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_COURSE, VALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM,
                        INVALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_COURSE, VALID_NOTE,
                       VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM,
                        VALID_PHONE, INVALID_EMAIL, VALID_ALIAS, VALID_COURSE, VALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAlias_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM,
                        VALID_PHONE, VALID_EMAIL, INVALID_ALIAS, VALID_COURSE, VALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = Alias.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCourse_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, INVALID_COURSE, VALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = Course.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_COURSE, INVALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = Note.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTelegram_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_TELEGRAM,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_COURSE, VALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = Telegram.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTelegram_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_COURSE, VALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Telegram.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidWebsite_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_TELEGRAM,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_COURSE, VALID_NOTE,
                        INVALID_WEBSITE, VALID_TAGS);
        String expectedMessage = Website.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedModule> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedModule(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_COURSE, VALID_NOTE,
                        VALID_TELEGRAM, VALID_WEBSITE, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
