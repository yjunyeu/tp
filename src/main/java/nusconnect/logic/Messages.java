package nusconnect.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nusconnect.logic.parser.Prefix;
import nusconnect.model.person.Person;

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
        person.getMajor().ifPresent(major -> builder.append("; Major: ").append(major));
        person.getNote().ifPresent(note -> builder.append("; Note: ").append(note));
        person.getWebsite().ifPresent(website -> builder.append("; Website: ").append(website));
        if (!person.getModules().isEmpty()) {
            builder.append("; Modules: ");
            person.getModules().forEach(builder::append);
        }
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
