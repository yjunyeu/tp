package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.commands.CommandTestUtil.ALIAS_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.ALIAS_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.COURSE_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.COURSE_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_ALIAS_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_COURSE_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_TELEGRAM_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_WEBSITE_DESC;
import static nusconnect.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.NOTE_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static nusconnect.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static nusconnect.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static nusconnect.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static nusconnect.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.TELEGRAM_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_ALIAS_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MODULE_CS2103T;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MODULE_CS2106;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.WEBSITE_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.WEBSITE_DESC_BOB;
import static nusconnect.logic.parser.CliSyntax.PREFIX_ALIAS;
import static nusconnect.logic.parser.CliSyntax.PREFIX_COURSE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static nusconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static nusconnect.logic.parser.CliSyntax.PREFIX_NOTE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_PHONE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static nusconnect.logic.parser.CliSyntax.PREFIX_WEBSITE;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nusconnect.testutil.TypicalPersons.AMY;
import static nusconnect.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import nusconnect.logic.Messages;
import nusconnect.logic.commands.AddCommand;
import nusconnect.model.module.Module;
import nusconnect.model.person.Alias;
import nusconnect.model.person.Course;
import nusconnect.model.person.Email;
import nusconnect.model.person.Name;
import nusconnect.model.person.Person;
import nusconnect.model.person.Phone;
import nusconnect.model.person.Telegram;
import nusconnect.model.person.Website;
import nusconnect.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withModules(VALID_MODULE_CS2106).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // multiple Modules - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withModules(VALID_MODULE_CS2106,
                VALID_MODULE_CS2103T).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ALIAS_DESC_BOB + COURSE_DESC_BOB
                        + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ALIAS_DESC_BOB
                + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple alias
        assertParseFailure(parser, ALIAS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ALIAS));

        // multiple course
        assertParseFailure(parser, COURSE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COURSE));

        // multiple note
        assertParseFailure(parser, NOTE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOTE));

        // multiple telegram
        assertParseFailure(parser, TELEGRAM_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // multiple website
        assertParseFailure(parser, WEBSITE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WEBSITE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + NAME_DESC_AMY + ALIAS_DESC_AMY + COURSE_DESC_AMY + NOTE_DESC_AMY
                        + TELEGRAM_DESC_AMY + WEBSITE_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ALIAS,
                        PREFIX_COURSE, PREFIX_NOTE, PREFIX_TELEGRAM, PREFIX_WEBSITE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid alias
        assertParseFailure(parser, INVALID_ALIAS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ALIAS));

        // invalid course
        assertParseFailure(parser, INVALID_COURSE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COURSE));

        // invalid telegram
        assertParseFailure(parser, INVALID_TELEGRAM_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // invalid website
        assertParseFailure(parser, INVALID_WEBSITE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WEBSITE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid alias
        assertParseFailure(parser, validExpectedPersonString + INVALID_ALIAS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ALIAS));

        // invalid course
        assertParseFailure(parser, validExpectedPersonString + INVALID_COURSE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COURSE));

        // invalid telegram
        assertParseFailure(parser, validExpectedPersonString + INVALID_TELEGRAM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // invalid website
        assertParseFailure(parser, validExpectedPersonString + INVALID_WEBSITE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WEBSITE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero Modules
        Person expectedPerson = new PersonBuilder(AMY).withModules().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ALIAS_DESC_AMY
                + COURSE_DESC_AMY + NOTE_DESC_AMY + TELEGRAM_DESC_AMY + WEBSITE_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ALIAS_DESC_BOB
                + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ALIAS_DESC_BOB
                + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ALIAS_DESC_BOB
                + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB,
                expectedMessage);

        // missing alias prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ALIAS_BOB
                        + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB,
                expectedMessage);

        // missing course prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ALIAS_DESC_BOB
                        + VALID_COURSE_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB,
                expectedMessage);

        // missing note prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ALIAS_DESC_BOB
                        + COURSE_DESC_BOB + VALID_NOTE_BOB + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB,
                expectedMessage);

        // missing telegram prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ALIAS_DESC_BOB
                        + COURSE_DESC_BOB + NOTE_DESC_BOB + VALID_TELEGRAM_BOB + WEBSITE_DESC_BOB,
                expectedMessage);

        // missing website prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ALIAS_DESC_BOB
                        + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB + VALID_WEBSITE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ALIAS_BOB
                + VALID_COURSE_BOB + VALID_NOTE_BOB + VALID_TELEGRAM_BOB + VALID_WEBSITE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB
                + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB
                + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB
                + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid alias
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ALIAS_DESC + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB
                + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Alias.MESSAGE_CONSTRAINTS);

        // invalid course
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ALIAS_DESC_BOB + INVALID_COURSE_DESC + NOTE_DESC_BOB + TELEGRAM_DESC_BOB
                + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Course.MESSAGE_CONSTRAINTS);

        // invalid telegram
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + INVALID_TELEGRAM_DESC
                + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Telegram.MESSAGE_CONSTRAINTS);

        // invalid website
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB
                + INVALID_WEBSITE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Website.MESSAGE_CONSTRAINTS);


        // invalid module
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB
                + WEBSITE_DESC_BOB + INVALID_TAG_DESC, Module.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB
                + INVALID_WEBSITE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB + TELEGRAM_DESC_BOB
                + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
