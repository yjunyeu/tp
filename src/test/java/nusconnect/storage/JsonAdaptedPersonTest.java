package nusconnect.storage;

import static nusconnect.logic.parser.CliSyntax.PREFIX_ALIAS;
import static nusconnect.logic.parser.CliSyntax.PREFIX_MAJOR;
import static nusconnect.logic.parser.CliSyntax.PREFIX_NOTE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static nusconnect.logic.parser.CliSyntax.PREFIX_WEBSITE;
import static nusconnect.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static nusconnect.testutil.Assert.assertThrows;
import static nusconnect.testutil.TypicalPersons.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nusconnect.commons.exceptions.IllegalValueException;
import nusconnect.model.person.Alias;
import nusconnect.model.person.Major;
import nusconnect.model.person.Email;
import nusconnect.model.person.Name;
import nusconnect.model.person.Note;
import nusconnect.model.person.Person;
import nusconnect.model.person.Phone;
import nusconnect.model.person.Telegram;
import nusconnect.model.person.Website;
import nusconnect.testutil.PersonBuilder;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ALIAS = " " + PREFIX_ALIAS;
    private static final String INVALID_MAJOR = " " + PREFIX_MAJOR;
    private static final String INVALID_NOTE = " " + PREFIX_NOTE;
    private static final String INVALID_TELEGRAM = " " + PREFIX_TELEGRAM;
    private static final String INVALID_WEBSITE = " " + PREFIX_WEBSITE;

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_TELEGRAM = BENSON.getTelegram().toString();
    private static final String VALID_PHONE = BENSON.getPhone().map(Phone::toString).orElse(null);
    private static final String VALID_EMAIL = BENSON.getEmail().map(Email::toString).orElse(null);
    private static final String VALID_ALIAS = BENSON.getAlias().map(Alias::toString).orElse(null);
    private static final String VALID_MAJOR = BENSON.getMajor().map(Major::toString).orElse(null);
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
    public void toModelType_invalidName_stillReturnsPerson() throws Exception {
        Person person = new PersonBuilder()
                .withName(INVALID_NAME)
                .withTelegram(VALID_TELEGRAM)
                .withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL)
                .withAlias(VALID_ALIAS)
                .withMajor(VALID_MAJOR)
                .withNote(VALID_NOTE)
                .withWebsite(VALID_WEBSITE)
                .build();
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(person);
        assertEquals(person, jsonPerson.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_TELEGRAM,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_MAJOR, VALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void invalidPhone_stillReturnsPerson() throws Exception {
        Person person = new PersonBuilder()
                .withName(VALID_NAME)
                .withTelegram(VALID_TELEGRAM)
                .withPhone(INVALID_PHONE)
                .withEmail(VALID_EMAIL)
                .withAlias(VALID_ALIAS)
                .withMajor(VALID_MAJOR)
                .withNote(VALID_NOTE)
                .withWebsite(VALID_WEBSITE)
                .build();
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(person);
        assertEquals(person, jsonPerson.toModelType());
    }

    @Test
    public void invalidEmail_stillReturnsPerson() throws Exception {
        Person person = new PersonBuilder()
                .withName(VALID_NAME)
                .withTelegram(VALID_TELEGRAM)
                .withPhone(VALID_PHONE)
                .withEmail(INVALID_EMAIL)
                .withAlias(VALID_ALIAS)
                .withMajor(VALID_MAJOR)
                .withNote(VALID_NOTE)
                .withWebsite(VALID_WEBSITE)
                .build();
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(person);
        assertEquals(person, jsonPerson.toModelType());
    }

    @Test
    public void invalidAlias_stillReturnsPerson() throws Exception {
        Person person = new PersonBuilder()
                .withName(VALID_NAME)
                .withTelegram(VALID_TELEGRAM)
                .withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL)
                .withAlias(INVALID_ALIAS)
                .withMajor(VALID_MAJOR)
                .withNote(VALID_NOTE)
                .withWebsite(VALID_WEBSITE)
                .build();
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(person);
        assertEquals(person, jsonPerson.toModelType());
    }


    @Test
    public void invalidCourse_stillReturnsPerson() throws Exception {
        Person person = new PersonBuilder()
                .withName(VALID_NAME)
                .withTelegram(VALID_TELEGRAM)
                .withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL)
                .withAlias(VALID_ALIAS)
                .withMajor(INVALID_MAJOR)
                .withNote(VALID_NOTE)
                .withWebsite(VALID_WEBSITE)
                .build();
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(person);
        assertEquals(person, jsonPerson.toModelType());
    }

    @Test
    public void invalidTelegram_stillReturnsPerson() throws Exception {
        Person person = new PersonBuilder()
                .withName(VALID_NAME)
                .withTelegram(INVALID_TELEGRAM)
                .withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL)
                .withAlias(VALID_ALIAS)
                .withMajor(VALID_MAJOR)
                .withNote(VALID_NOTE)
                .withWebsite(VALID_WEBSITE)
                .build();
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(person);
        assertEquals(person, jsonPerson.toModelType());
    }

    @Test
    public void toModelType_nullTelegram_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null,
                        VALID_PHONE, VALID_EMAIL, VALID_ALIAS, VALID_MAJOR, VALID_NOTE,
                        VALID_WEBSITE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Telegram.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void invalidWebsite_stillReturnsPerson() throws Exception {
        Person person = new PersonBuilder()
                .withName(VALID_NAME)
                .withTelegram(VALID_TELEGRAM)
                .withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL)
                .withAlias(VALID_ALIAS)
                .withMajor(VALID_MAJOR)
                .withNote(VALID_NOTE)
                .withWebsite(INVALID_WEBSITE)
                .build();
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(person);
        assertEquals(person, jsonPerson.toModelType());
    }

}
