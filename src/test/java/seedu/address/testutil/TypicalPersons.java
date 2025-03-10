package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withAlias("AliceAlias")
            .withCourse("Engineering")
            .withNote("Enjoys reading")
            .withTelegram("@alice")
            .withWebsite("https://alicepauline.com")
            .withTags("friends").build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withAlias("BensonAlias")
            .withCourse("Comp Science")
            .withNote("Great at teamwork")
            .withTelegram("@benson")
            .withWebsite("https://bensonmeier.com")
            .withTags("owesMoney", "friends").build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAlias("CarlAlias")
            .withCourse("Business")
            .withNote("Detail-oriented")
            .withTelegram("@carl")
            .withWebsite("https://carlkurz.com")
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAlias("DanielAlias")
            .withCourse("Mech Eng")
            .withNote("Enthusiastic learner")
            .withTelegram("@daniel")
            .withWebsite("https://danielmeier.com")
            .withTags("friends").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAlias("ElleAlias")
            .withCourse("Comp Science")
            .withNote("Creative thinker")
            .withTelegram("@elle")
            .withWebsite("https://ellemeyer.com")
            .build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAlias("FionaAlias")
            .withCourse("Engineering")
            .withNote("Tech savvy")
            .withTelegram("@fiona")
            .withWebsite("https://fionakunz.com")
            .build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAlias("GeorgeAlias")
            .withCourse("Business")
            .withNote("Sports enthusiast")
            .withTelegram("@george")
            .withWebsite("https://georgebest.com")
            .withTags("colleagues").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAlias("HoonAlias")
            .withCourse("Engineering")
            .withNote("Friendly")
            .withTelegram("@hoon")
            .withWebsite("https://hoonmeier.com")
            .build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAlias("IdaAlias")
            .withCourse("Comp Science")
            .withNote("Reliable")
            .withTelegram("@ida")
            .withWebsite("https://idamueller.com")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAlias(VALID_ALIAS_AMY)
            .withCourse(VALID_COURSE_AMY)
            .withNote(VALID_NOTE_AMY)
            .withTelegram(VALID_TELEGRAM_AMY)
            .withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND)
            .build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAlias(VALID_ALIAS_BOB)
            .withCourse(VALID_COURSE_BOB)
            .withNote(VALID_NOTE_BOB)
            .withTelegram(VALID_TELEGRAM_BOB)
            .withWebsite(VALID_WEBSITE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
