package nusconnect.model.person;

import static nusconnect.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import nusconnect.commons.util.ToStringBuilder;
import nusconnect.model.module.Module;

/**
 * Represents a Person in the address book.
 * Guarantees: name is present, field values are immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Telegram telegram;

    // Data fields
    private final Optional<Phone> phone;
    private final Optional<Email> email;
    private final Optional<Alias> alias;
    private final Optional<Course> course;
    private final Optional<Note> note;
    private final Optional<Website> website;
    private final Set<Module> modules = new HashSet<>();

    /**
     * Constructs a {@code Person} with the specified details.
     *
     * @param name the name of the person (must not be null)
     * @param telegram the telegram of the person (must not be null)
     * @param phone the phone number of the person (may be null)
     * @param email the email address of the person (may be null)
     * @param alias the alias of the person (may be null)
     * @param course the course the person is enrolled in (may be null)
     * @param note any additional notes about the person (may be null)
     * @param website the website of the person (may be null)
     * @param modules the set of modules the person is enrolled in (must not be null, but the set may be empty)
     */
    public Person(Name name, Telegram telegram, Phone phone, Email email, Alias alias, Course course, Note note,
                   Website website, Set<Module> modules) {
        requireAllNonNull(name, telegram);
        this.name = name;
        this.telegram = telegram;
        this.phone = Optional.ofNullable(phone);
        this.email = Optional.ofNullable(email);
        this.alias = Optional.ofNullable(alias);
        this.course = Optional.ofNullable(course);
        this.note = Optional.ofNullable(note);
        this.website = Optional.ofNullable(website);
        this.modules.addAll(modules);
    }

    public Name getName() {
        return name;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public Optional<Phone> getPhone() {
        return phone;
    }

    public Optional<Email> getEmail() {
        return email;
    }

    public Optional<Alias> getAlias() {
        return alias;
    }

    public Optional<Course> getCourse() {
        return course;
    }

    public Optional<Note> getNote() {
        return note;
    }

    public Optional<Website> getWebsite() {
        return website;
    }

    /**
     * Returns an immutable module set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Module> getModules() {
        return Collections.unmodifiableSet(modules);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && telegram.equals(otherPerson.telegram)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && alias.equals(otherPerson.alias)
                && course.equals(otherPerson.course)
                && note.equals(otherPerson.note)
                && website.equals(otherPerson.website)
                && modules.equals(otherPerson.modules);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, telegram, phone, email, alias, course, note, website, modules);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("telegram", telegram)
                .add("phone", phone.orElse(null))
                .add("email", email.orElse(null))
                .add("alias", alias.orElse(null))
                .add("course", course.orElse(null))
                .add("note", note.orElse(null))
                .add("website", website.orElse(null))
                .add("modules", modules)
                .toString();
    }
}
