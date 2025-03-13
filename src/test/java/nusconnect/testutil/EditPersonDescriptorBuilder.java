package nusconnect.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nusconnect.logic.commands.EditCommand.EditPersonDescriptor;
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
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAlias(person.getAlias());
        descriptor.setCourse(person.getCourse());
        descriptor.setNote(person.getNote());
        descriptor.setTelegram(person.getTelegram());
        descriptor.setWebsite(person.getWebsite());
        descriptor.setModules(person.getModules());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Alias} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAlias(String alias) {
        descriptor.setAlias(new Alias(alias));
        return this;
    }

    /**
     * Sets the {@code Course} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withCourse(String course) {
        descriptor.setCourse(new Course(course));
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withTelegram(String telegram) {
        descriptor.setTelegram(new Telegram(telegram));
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withWebsite(String website) {
        descriptor.setWebsite(new Website(website));
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
