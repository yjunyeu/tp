package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.CliSyntax.PREFIX_ALIAS;
import static nusconnect.logic.parser.CliSyntax.PREFIX_COURSE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static nusconnect.logic.parser.CliSyntax.PREFIX_MODULE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static nusconnect.logic.parser.CliSyntax.PREFIX_NOTE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_PHONE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static nusconnect.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.Set;
import java.util.stream.Stream;

import nusconnect.logic.commands.AddCommand;
import nusconnect.logic.parser.exceptions.ParseException;
import nusconnect.model.module.Module;
import nusconnect.model.person.Alias;
import nusconnect.model.person.Course;
import nusconnect.model.person.Email;
import nusconnect.model.person.Name;
import nusconnect.model.person.Note;
import nusconnect.model.person.Person;
import nusconnect.model.person.Phone;
import nusconnect.model.person.Telegram;
import nusconnect.model.person.Website;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ALIAS,
                        PREFIX_COURSE, PREFIX_NOTE, PREFIX_WEBSITE, PREFIX_MODULE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TELEGRAM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ALIAS,
                PREFIX_COURSE, PREFIX_NOTE, PREFIX_WEBSITE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Telegram telegram = ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get());

        Phone phone = null;
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }
        Email email = null;
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }
        Alias alias = null;
        if (argMultimap.getValue(PREFIX_ALIAS).isPresent()) {
            alias = ParserUtil.parseAlias(argMultimap.getValue(PREFIX_ALIAS).get());
        }

        Course course = null;
        if (argMultimap.getValue(PREFIX_COURSE).isPresent()) {
            course = ParserUtil.parseCourse(argMultimap.getValue(PREFIX_COURSE).get());
        }

        Note note = null;
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        }

        Website website = null;
        if (argMultimap.getValue(PREFIX_WEBSITE).isPresent()) {
            website = ParserUtil.parseWebsite(argMultimap.getValue(PREFIX_WEBSITE).get());
        }

        Set<Module> moduleList = ParserUtil.parseModules(argMultimap.getAllValues(PREFIX_MODULE));

        Person person = new Person(name, telegram, phone, email, alias, course, note, website, moduleList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
