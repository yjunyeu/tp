package nusconnect.testutil;

import static nusconnect.logic.parser.CliSyntax.PREFIX_ALIAS;
import static nusconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static nusconnect.logic.parser.CliSyntax.PREFIX_MAJOR;
import static nusconnect.logic.parser.CliSyntax.PREFIX_MODULE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_NAME;
import static nusconnect.logic.parser.CliSyntax.PREFIX_NOTE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_PHONE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static nusconnect.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.Set;

import nusconnect.logic.commands.AddCommand;
import nusconnect.logic.commands.EditCommand.EditPersonDescriptor;
import nusconnect.model.module.Module;
import nusconnect.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_TELEGRAM + person.getTelegram().value + " ");
        sb.append(person.getPhone().map(p -> PREFIX_PHONE + p.value + " ").orElse(""));
        sb.append(person.getEmail().map(e -> PREFIX_EMAIL + e.value + " ").orElse(""));
        sb.append(person.getAlias().map(a -> PREFIX_ALIAS + a.value + " ").orElse(""));
        sb.append(person.getMajor().map(c -> PREFIX_MAJOR + c.value + " ").orElse(""));
        sb.append(person.getNote().map(n -> PREFIX_NOTE + n.value + " ").orElse(""));
        sb.append(person.getWebsite().map(w -> PREFIX_WEBSITE + w.value + " ").orElse(""));

        person.getModules().stream().forEach(
            s -> sb.append(PREFIX_MODULE + s.moduleName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getIsNameEdited()) {
            sb.append(PREFIX_NAME).append(descriptor.getName()).append(" ");
        }

        if (descriptor.getIsPhoneEdited()) {
            sb.append(PREFIX_PHONE).append(descriptor.getPhone()).append(" ");
        }

        if (descriptor.getIsEmailEdited()) {
            sb.append(PREFIX_EMAIL).append(descriptor.getEmail()).append(" ");
        }

        if (descriptor.getIsAliasEdited()) {
            sb.append(PREFIX_ALIAS).append(descriptor.getAlias()).append(" ");
        }

        if (descriptor.getIsMajorEdited()) {
            sb.append(PREFIX_MAJOR).append(descriptor.getMajor()).append(" ");
        }

        if (descriptor.getIsNoteEdited()) {
            sb.append(PREFIX_NOTE).append(descriptor.getNote()).append(" ");
        }

        if (descriptor.getIsTelegramEdited()) {
            sb.append(PREFIX_TELEGRAM).append(descriptor.getTelegram()).append(" ");
        }

        if (descriptor.getIsWebsiteEdited()) {
            sb.append(PREFIX_WEBSITE).append(descriptor.getWebsite()).append(" ");
        }

        if (descriptor.getModules().isPresent()) {
            Set<Module> modules = descriptor.getModules().get();
            if (modules.isEmpty()) {
                sb.append(PREFIX_MODULE);
            } else {
                modules.forEach(s -> sb.append(PREFIX_MODULE).append(s.moduleName).append(" "));
            }
        }
        return sb.toString();
    }
}
