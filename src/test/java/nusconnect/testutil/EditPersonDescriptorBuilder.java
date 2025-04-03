package nusconnect.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nusconnect.logic.commands.EditCommand.EditPersonDescriptor;
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
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setTelegram(person.getTelegram());
        descriptor.setPhone(person.getPhone().orElse(null));
        descriptor.setEmail(person.getEmail().orElse(null));
        descriptor.setAlias(person.getAlias().orElse(null));
        descriptor.setMajor(person.getMajor().orElse(null));
        descriptor.setNote(person.getNote().orElse(null));
        descriptor.setWebsite(person.getWebsite().orElse(null));
        descriptor.setModules(person.getModules());
        descriptor.setIsNameEdited(true);
        descriptor.setIsTelegramEdited(true);
        descriptor.setIsPhoneEdited(true);
        descriptor.setIsEmailEdited(true);
        descriptor.setIsAliasEdited(true);
        descriptor.setIsMajorEdited(true);
        descriptor.setIsNoteEdited(true);
        descriptor.setIsWebsiteEdited(true);
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        descriptor.setIsNameEdited(true);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        descriptor.setIsPhoneEdited(true);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        descriptor.setIsEmailEdited(true);
        return this;
    }

    /**
     * Sets the {@code Alias} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAlias(String alias) {
        descriptor.setAlias(new Alias(alias));
        descriptor.setIsAliasEdited(true);
        return this;
    }

    /**
     * Sets the {@code Major} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withMajor(String course) {
        descriptor.setMajor(new Major(course));
        descriptor.setIsMajorEdited(true);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        descriptor.setIsNoteEdited(true);
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withTelegram(String telegram) {
        descriptor.setTelegram(new Telegram(telegram));
        descriptor.setIsTelegramEdited(true);
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withWebsite(String website) {
        descriptor.setWebsite(new Website(website));
        descriptor.setIsWebsiteEdited(true);
        return this;
    }

    /**
     * Parses the {@code Modules} into a {@code Set<Module>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Module> moduleSet = Stream.of(tags).map(Module::new).collect(Collectors.toSet());
        descriptor.setModules(moduleSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
