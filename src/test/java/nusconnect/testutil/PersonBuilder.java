package nusconnect.testutil;

import java.util.HashSet;
import java.util.Set;

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
import nusconnect.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_TELEGRAM = "@amyamybee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ALIAS = "Amee";
    public static final String DEFAULT_COURSE = "Computer Science";
    public static final String DEFAULT_NOTE = "Bestie!";
    public static final String DEFAULT_WEBSITE = "aimee.com";

    private Name name;
    private Telegram telegram;
    private Phone phone;
    private Email email;
    private Alias alias;
    private Course course;
    private Note note;

    private Website website;
    private Set<Module> modules;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        telegram = new Telegram(DEFAULT_TELEGRAM);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        alias = new Alias(DEFAULT_ALIAS);
        course = new Course(DEFAULT_COURSE);
        note = new Note(DEFAULT_NOTE);
        website = new Website(DEFAULT_WEBSITE);
        modules = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        telegram = personToCopy.getTelegram();
        phone = personToCopy.getPhone().orElse(null);
        email = personToCopy.getEmail().orElse(null);
        alias = personToCopy.getAlias().orElse(null);
        course = personToCopy.getCourse().orElse(null);
        note = personToCopy.getNote().orElse(null);
        website = personToCopy.getWebsite().orElse(null);
        modules = new HashSet<>(personToCopy.getModules());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String telegram) {
        this.telegram = new Telegram(telegram);
        return this;
    }

    /**
     * Parses the {@code modules} into a {@code Set<Module>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withModules(String ... modules) {
        this.modules = SampleDataUtil.getModuleSet(modules);
        return this;
    }

    /**
     * Sets the {@code Alias} of the {@code Person} that we are building.
     */
    public PersonBuilder withAlias(String alias) {
        this.alias = new Alias(alias);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Course} of the {@code Person} that we are building.
     */
    public PersonBuilder withCourse(String course) {
        this.course = new Course(course);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code Person} that we are building.
     */
    public PersonBuilder withWebsite(String website) {
        this.website = new Website(website);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, telegram, phone, email, alias, course, note, website, modules);
    }

}
