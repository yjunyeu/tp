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
    private Label telegramLabel;
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
        telegramLabel.setText("");
        phoneLabel.setText("");
        emailLabel.setText("");
        aliasLabel.setText("");
        courseLabel.setText("");
        noteLabel.setText("");
        websiteLabel.setText("");
    }

    /**
     * Updates the UI with the details of the given {@code person}.
     */
    public void setPersonDetails(Person person) {
        if (person != null) {
            nameLabel.setText(person.getName().fullName);
            telegramLabel.setText(person.getTelegram().value);
            phoneLabel.setText(person.getPhone().map(p -> p.value).orElse("-"));
            emailLabel.setText(person.getEmail().map(e -> e.value).orElse("-"));
            aliasLabel.setText(person.getAlias().map(a -> a.value).orElse("-"));
            courseLabel.setText(person.getCourse().map(c -> c.value).orElse("-"));
            noteLabel.setText(person.getNote().map(n -> n.value).orElse("-"));
            websiteLabel.setText(person.getWebsite().map(w -> w.value).orElse("-"));
        } else {
            setDefaultDetails();
        }
    }
}
