package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * The UI component that is responsible for showing the details of a person.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);

    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label aliasLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private Label noteLabel;
    @FXML
    private Label telegramLabel;
    @FXML
    private Label websiteLabel;

    /**
     * Creates a {@code PersonDetailsPanel}.
     */
    public PersonDetailsPanel() {
        super(FXML);
        setDefaultDetails();
    }

    /**
     * Sets default details when no person is selected.
     */
    private void setDefaultDetails() {
        nameLabel.setText("Select a person");
        phoneLabel.setText("");
        emailLabel.setText("");
        aliasLabel.setText("");
        courseLabel.setText("");
        noteLabel.setText("");
        telegramLabel.setText("");
        websiteLabel.setText("");
    }

    /**
     * Updates the UI with the details of the given {@code person}.
     */
    public void setPersonDetails(Person person) {
        if (person != null) {
            nameLabel.setText(person.getName().fullName);
            phoneLabel.setText("Number: " + person.getPhone().value);
            emailLabel.setText("Email: " + person.getEmail().value);
            aliasLabel.setText("Alias: " + person.getAlias().value);
            courseLabel.setText("Course: " + person.getCourse().value);
            noteLabel.setText("Note: " + person.getNote().value);
            telegramLabel.setText("Telegram: " + person.getTelegram().value);
            websiteLabel.setText("Website: " + person.getWebsite().value);
        } else {
            setDefaultDetails();
        }
    }
}
