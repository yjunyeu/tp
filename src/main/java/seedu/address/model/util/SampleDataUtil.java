package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Alias;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
import seedu.address.model.person.Website;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Alias("alexAlias"), new Course("Engineering"), new Note("Enjoys math"),
                    new Telegram("@alexyeoh"), new Website("https://alexyeoh.com"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Alias("berniceAlias"), new Course("Comp Science"), new Note("Loves coding"),
                    new Telegram("@berniceyu"), new Website("https://berniceyu.com"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Alias("charlotteAlias"), new Course("Business"), new Note("Enjoys design"),
                    new Telegram("@charlotte"), new Website("https://charlotteoliveiro.com"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Alias("davidAlias"), new Course("Mech Eng"), new Note("Tech enthusiast"),
                    new Telegram("@davidli"), new Website("https://davidli.com"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Alias("irfanAlias"), new Course("Engineering"), new Note("Loves music"),
                    new Telegram("@irfanibrahim"), new Website("https://irfanibrahim.com"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Alias("royAlias"), new Course("Comp Science"), new Note("Enjoys reading"),
                    new Telegram("@roybalakrishnan"), new Website("https://roybalakrishnan.com"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
