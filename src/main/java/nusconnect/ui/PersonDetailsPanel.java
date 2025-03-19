package nusconnect.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import nusconnect.commons.core.LogsCenter;
import nusconnect.model.person.Person;

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
        nameLabel.setText("Use view to select");
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
            phoneLabel.setText(person.getPhone().value);
            emailLabel.setText(person.getEmail().value);
            aliasLabel.setText(person.getAlias().value);
            courseLabel.setText(person.getCourse().value);
            noteLabel.setText(person.getNote().value);
            telegramLabel.setText(person.getTelegram().value);
            websiteLabel.setText(person.getWebsite().value);
        } else {
            setDefaultDetails();
        }
    }
}
