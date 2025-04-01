package nusconnect.logic.parser;

import static java.util.Objects.requireNonNull;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import nusconnect.commons.core.index.Index;
import nusconnect.logic.commands.EditCommand;
import nusconnect.logic.commands.EditCommand.EditPersonDescriptor;
import nusconnect.logic.parser.exceptions.ParseException;
import nusconnect.model.module.Module;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TELEGRAM,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ALIAS, PREFIX_COURSE,
                        PREFIX_NOTE, PREFIX_WEBSITE, PREFIX_MODULE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TELEGRAM,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ALIAS, PREFIX_COURSE,
                PREFIX_NOTE, PREFIX_WEBSITE);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setIsNameEdited(true);
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            editPersonDescriptor.setIsNameEdited(true);
            editPersonDescriptor.setTelegram(ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get()));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setIsPhoneEdited(true);
            String phoneValue = argMultimap.getValue(PREFIX_PHONE).get();
            if (!phoneValue.isEmpty()) {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(phoneValue));
            } else {
                editPersonDescriptor.setPhone(null);
            }
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setIsEmailEdited(true);
            String emailValue = argMultimap.getValue(PREFIX_EMAIL).get();
            if (!emailValue.isEmpty()) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(emailValue));
            } else {
                editPersonDescriptor.setEmail(null);
            }
        }

        if (argMultimap.getValue(PREFIX_ALIAS).isPresent()) {
            editPersonDescriptor.setIsAliasEdited(true);
            String aliasValue = argMultimap.getValue(PREFIX_ALIAS).get();
            if (!aliasValue.isEmpty()) {
                editPersonDescriptor.setAlias(ParserUtil.parseAlias(aliasValue));
            } else {
                editPersonDescriptor.setAlias(null);
            }
        }

        if (argMultimap.getValue(PREFIX_COURSE).isPresent()) {
            editPersonDescriptor.setIsCourseEdited(true);
            String courseValue = argMultimap.getValue(PREFIX_COURSE).get();
            if (!courseValue.isEmpty()) {
                editPersonDescriptor.setCourse(ParserUtil.parseCourse(courseValue));
            } else {
                editPersonDescriptor.setCourse(null);
            }
        }

        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editPersonDescriptor.setIsNoteEdited(true);
            String noteValue = argMultimap.getValue(PREFIX_NOTE).get();
            if (!noteValue.isEmpty()) {
                editPersonDescriptor.setNote(ParserUtil.parseNote(noteValue));
            } else {
                editPersonDescriptor.setNote(null);
            }
        }

        if (argMultimap.getValue(PREFIX_WEBSITE).isPresent()) {
            editPersonDescriptor.setIsWebsiteEdited(true);
            String websiteValue = argMultimap.getValue(PREFIX_WEBSITE).get();
            if (!websiteValue.isEmpty()) {
                editPersonDescriptor.setWebsite(ParserUtil.parseWebsite(websiteValue));
            } else {
                editPersonDescriptor.setWebsite(null);
            }
        }

        parseModulesForEdit(argMultimap.getAllValues(PREFIX_MODULE)).ifPresent(editPersonDescriptor::setModules);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> modules} into a {@code Set<Module>} if {@code modules} is non-empty.
     * If {@code modules} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Module>} containing zero modules.
     */
    private Optional<Set<Module>> parseModulesForEdit(Collection<String> modules) throws ParseException {
        assert modules != null;

        if (modules.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> moduleSet = modules.size() == 1 && modules.contains("") ? Collections.emptySet() : modules;
        return Optional.of(ParserUtil.parseModules(moduleSet));
    }

}
