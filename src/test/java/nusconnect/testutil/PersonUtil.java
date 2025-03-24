package nusconnect.testutil;

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
        sb.append(person.getCourse().map(c -> PREFIX_COURSE + c.value + " ").orElse(""));
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
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAlias().ifPresent(alias -> sb.append(PREFIX_ALIAS).append(alias.value).append(" "));
        descriptor.getCourse().ifPresent(course -> sb.append(PREFIX_COURSE).append(course.value).append(" "));
        descriptor.getNote().ifPresent(note -> sb.append(PREFIX_NOTE).append(note.value).append(" "));
        descriptor.getTelegram().ifPresent(telegram -> sb.append(PREFIX_TELEGRAM).append(telegram.value).append(" "));
        descriptor.getWebsite().ifPresent(website -> sb.append(PREFIX_WEBSITE).append(website.value).append(" "));

        if (descriptor.getTags().isPresent()) {
            Set<Module> modules = descriptor.getTags().get();
            if (modules.isEmpty()) {
                sb.append(PREFIX_MODULE);
            } else {
                modules.forEach(s -> sb.append(PREFIX_MODULE).append(s.moduleName).append(" "));
            }
        }
        return sb.toString();
    }
}
