package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.module.Module;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Alias alias;

    // Data fields
    private final Course course;
    private final Note note;
    private final Telegram telegram;
    private final Website website;
    private final Set<Module> modules = new HashSet<>();


    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Alias alias, Course course, Note note,
                  Telegram telegram, Website website, Set<Module> modules) {
        requireAllNonNull(name, phone, email, alias, course, note, telegram, website);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.alias = alias;
        this.course = course;
        this.note = note;
        this.telegram = telegram;
        this.website = website;
        this.modules.addAll(modules);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() { return email; }

    public Alias getAlias() {
        return alias;
    }

    public Course getCourse() {
        return course;
    }

    public Note getNote() {
        return note;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public Website getWebsite() {
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
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && alias.equals(otherPerson.alias)
                && course.equals(otherPerson.course)
                && note.equals(otherPerson.note)
                && telegram.equals(otherPerson.telegram)
                && website.equals(otherPerson.website)
                && modules.equals(otherPerson.modules);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, alias, course, note, telegram, website, modules);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("alias", alias)
                .add("course", course)
                .add("note", note)
                .add("telegram", telegram)
                .add("website", website)
                .add("modules", modules)
                .toString();
    }

}
