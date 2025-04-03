package nusconnect.logic.parser;

import static java.util.Objects.requireNonNull;
import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.CliSyntax.PREFIX_ALIAS;
import static nusconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static nusconnect.logic.parser.CliSyntax.PREFIX_MAJOR;
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
     * Determines if a field needs to be parsed and updates the corresponding edited flag.
     * If the field is present, that means the field is being edited and sets the "edited" flag to true.
     * If the field's value is empty, it sets the field to null and returns false.
     * If the field's value is present, it returns true and lets the outer function catch any parsing errors.
     * If the field is not present, it returns false, and the "edited" flag is not set.
     *
     * @param prefix The prefix associated with the field to be parsed.
     * @param argMultimap The map containing the argument values.
     * @param setter A consumer that sets the parsed value of the field.
     * @param editedFlagSetter A consumer that sets the "edited" flag to true if the field is present.
     * @param <T> The type of the field being parsed (e.g., Phone, Email, etc.).
     * @return {@code true} if the field is present and not empty, {@code false} otherwise.
     */
    private <T> boolean determineIfFieldNeedsToBeParsed(Prefix prefix, ArgumentMultimap argMultimap,
                             Consumer<T> setter, Consumer<Boolean> editedFlagSetter) {
        return argMultimap.getValue(prefix).map(value -> {
            editedFlagSetter.accept(true);
            if (value.isEmpty()) {
                setter.accept(null);
                return false;
            } else {
                return true;
            }
        }).orElse(false);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TELEGRAM,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ALIAS, PREFIX_MAJOR,
                        PREFIX_NOTE, PREFIX_WEBSITE, PREFIX_MODULE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TELEGRAM,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ALIAS, PREFIX_MAJOR,
                PREFIX_NOTE, PREFIX_WEBSITE);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setIsNameEdited(true);
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            editPersonDescriptor.setIsTelegramEdited(true);
            editPersonDescriptor.setTelegram(ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get()));
        }

        // For optional fields apart from module, a helper function determines if the field needs to be parsed.
        // Exceptions are handled by the parser function and not the helper function.

        if (determineIfFieldNeedsToBeParsed(PREFIX_PHONE, argMultimap,
                editPersonDescriptor::setPhone, editPersonDescriptor::setIsPhoneEdited)) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        if (determineIfFieldNeedsToBeParsed(PREFIX_PHONE, argMultimap,
                editPersonDescriptor::setPhone, editPersonDescriptor::setIsPhoneEdited)) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        if (determineIfFieldNeedsToBeParsed(PREFIX_EMAIL, argMultimap,
                editPersonDescriptor::setEmail, editPersonDescriptor::setIsEmailEdited)) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (determineIfFieldNeedsToBeParsed(PREFIX_ALIAS, argMultimap,
                editPersonDescriptor::setAlias, editPersonDescriptor::setIsAliasEdited)) {
            editPersonDescriptor.setAlias(ParserUtil.parseAlias(argMultimap.getValue(PREFIX_ALIAS).get()));
        }

        if (determineIfFieldNeedsToBeParsed(PREFIX_MAJOR, argMultimap,
                editPersonDescriptor::setMajor, editPersonDescriptor::setIsMajorEdited)) {
            editPersonDescriptor.setMajor(ParserUtil.parseMajor(argMultimap.getValue(PREFIX_MAJOR).get()));
        }

        if (determineIfFieldNeedsToBeParsed(PREFIX_NOTE, argMultimap,
                editPersonDescriptor::setNote, editPersonDescriptor::setIsNoteEdited)) {
            editPersonDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }

        if (determineIfFieldNeedsToBeParsed(PREFIX_WEBSITE, argMultimap,
                editPersonDescriptor::setWebsite, editPersonDescriptor::setIsWebsiteEdited)) {
            editPersonDescriptor.setWebsite(ParserUtil.parseWebsite(argMultimap.getValue(PREFIX_WEBSITE).get()));
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
