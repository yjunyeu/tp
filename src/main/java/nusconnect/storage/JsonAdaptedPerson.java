package nusconnect.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nusconnect.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String telegram;
    private final String phone;
    private final String email;
    private final String alias;
    private final String course;
    private final String note;
    private final String website;
    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("telegram") String telegram,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("alias") String alias, @JsonProperty("course") String course,
                             @JsonProperty("note") String note, @JsonProperty("website") String website,
                             @JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.name = name;
        this.telegram = telegram;
        this.phone = phone;
        this.email = email;
        this.alias = alias;
        this.course = course;
        this.note = note;
        this.website = website;
        if (modules != null) {
            this.modules.addAll(modules);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        telegram = source.getTelegram().value;
        phone = source.getPhone().map(p -> p.value).orElse(null);
        email = source.getEmail().map(e -> e.value).orElse(null);
        alias = source.getAlias().map(a -> a.value).orElse(null);
        course = source.getCourse().map(c -> c.value).orElse(null);
        note = source.getNote().map(n -> n.value).orElse(null);
        website = source.getWebsite().map(w -> w.value).orElse(null);

        modules.addAll(source.getModules().stream()
                .map(JsonAdaptedModule::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Telegram.class.getSimpleName()));
        }
        if (!Telegram.isValidTelegram(telegram)) {
            throw new IllegalValueException(Telegram.MESSAGE_CONSTRAINTS);
        }
        final Telegram modelTelegram = new Telegram(telegram);

        final Phone modelPhone;
        if (phone != null) {
            if (!Phone.isValidPhone(phone)) {
                throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
            }
            modelPhone = new Phone(phone);
        } else {
            modelPhone = null;
        }

        final Email modelEmail;
        if (email != null) {
            if (!Email.isValidEmail(email)) {
                throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
            }
            modelEmail = new Email(email);
        } else {
            modelEmail = null;
        }

        final Alias modelAlias;
        if (alias != null) {
            if (!Alias.isValidAlias(alias)) {
                throw new IllegalValueException(Alias.MESSAGE_CONSTRAINTS);
            }
            modelAlias = new Alias(alias);
        } else {
            modelAlias = null;
        }

        final Course modelCourse;
        if (course != null) {
            if (!Course.isValidCourse(course)) {
                throw new IllegalValueException(Course.MESSAGE_CONSTRAINTS);
            }
            modelCourse = new Course(course);
        } else {
            modelCourse = null;
        }

        final Note modelNote;
        if (note != null) {
            if (!Note.isValidNote(note)) {
                throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
            }
            modelNote = new Note(note);
        } else {
            modelNote = null;
        }

        final Website modelWebsite;
        if (website != null) {
            if (!Website.isValidWebsite(website)) {
                throw new IllegalValueException(Website.MESSAGE_CONSTRAINTS);
            }
            modelWebsite = new Website(website);
        } else {
            modelWebsite = null;
        }

        final List<Module> personModules = new ArrayList<>();
        for (JsonAdaptedModule module : modules) {
            personModules.add(module.toModelType());
        }

        final Set<Module> modelModules = new HashSet<>(personModules);
        return new Person(modelName, modelTelegram, modelPhone, modelEmail, modelAlias, modelCourse, modelNote,
                modelWebsite, modelModules);
    }

}
