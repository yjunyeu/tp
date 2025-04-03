package nusconnect.logic;

import static nusconnect.commons.util.AppUtil.checkArgument;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nusconnect.logic.parser.Prefix;
import nusconnect.model.module.Module;
import nusconnect.model.person.Alias;
import nusconnect.model.person.Major;
import nusconnect.model.person.Email;
import nusconnect.model.person.Name;
import nusconnect.model.person.Note;
import nusconnect.model.person.Person;
import nusconnect.model.person.Phone;
import nusconnect.model.person.Telegram;
import nusconnect.model.person.Website;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid!";
    public static final String MESSAGE_INVALID_PERSON_TO_DELETE_INDEX =
            "The following person index provided is invalid: %1$s";

    public static final String MESSAGE_PERSON_LISTED_OVERVIEW = "%1$d person listed!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_NO_PERSON_LISTED_OVERVIEW = "No results found!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_GROUP_INDEX = "The group index provided is invalid";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields) + "!";
    }

    /**
     * Returns warning messages for any field that does not match the pre-set patterns.
     */
    public static String generateWarningForPatternMismatch(Person person) {
        final StringBuilder warnings = new StringBuilder();
        warnings.append(checkArgument(Name.isValidName(person.getName()), Name.MESSAGE_CONSTRAINTS));
        warnings.append(checkArgument(Telegram.isValidTelegram(person.getTelegram()), Telegram.MESSAGE_CONSTRAINTS));
        person.getPhone().map(phone -> warnings.append(checkArgument(Phone.isValidPhone(phone),
                Phone.MESSAGE_CONSTRAINTS)));
        person.getEmail().map(email -> warnings.append(checkArgument(Email.isValidEmail(email),
                Email.MESSAGE_CONSTRAINTS)));
        person.getAlias().map(alias -> warnings.append(checkArgument(Alias.isValidAlias(alias),
                Alias.MESSAGE_CONSTRAINTS)));
        person.getMajor().map(course -> warnings.append(checkArgument(Major.isValidMajor(course),
                Major.MESSAGE_CONSTRAINTS)));
        person.getNote().map(note -> warnings.append(checkArgument(Note.isValidNote(note),
                Note.MESSAGE_CONSTRAINTS)));
        person.getWebsite().map(website -> warnings.append(checkArgument(Website.isValidWebsite(website),
                Website.MESSAGE_CONSTRAINTS)));
        for (Module module : person.getModules()) {
            warnings.append(checkArgument(Module.isValidModuleName(module), Module.MESSAGE_CONSTRAINTS));
        }
        return warnings.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(person.getName())
                .append("; Telegram: ")
                .append(person.getTelegram());
        person.getPhone().ifPresent(phone -> builder.append("; Phone: ").append(phone));
        person.getEmail().ifPresent(email -> builder.append("; Email: ").append(email));
        person.getAlias().ifPresent(alias -> builder.append("; Alias: ").append(alias));
        person.getMajor().ifPresent(course -> builder.append("; Major: ").append(course));
        person.getNote().ifPresent(note -> builder.append("; Note: ").append(note));
        person.getWebsite().ifPresent(website -> builder.append("; Website: ").append(website));
        if (!person.getModules().isEmpty()) {
            builder.append("; Modules: ");
            person.getModules().forEach(builder::append);
        }
        builder.append("\n");
        builder.append(generateWarningForPatternMismatch(person));
        return builder.toString();
    }

    // Method to handle singular/plural "person" for the list overview
    public static String getPersonsListedOverview(int numPersons) {
        if (numPersons == 0) {
            return MESSAGE_NO_PERSON_LISTED_OVERVIEW;
        } else if (numPersons == 1) {
            return String.format(MESSAGE_PERSON_LISTED_OVERVIEW, numPersons);
        } else {
            return String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, numPersons);
        }
    }

}
