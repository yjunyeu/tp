package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * The UI component that is responsible for containing the list of groups.
 */
public class GroupPanel extends UiPart<Region> {
    private static final String FXML = "GroupPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupPanel.class);

    @FXML
    private ListView<String> groupListView;

    /**
     * Creates a {@code GroupPanel}.
     */
    public GroupPanel() {
        super(FXML);
        groupListView.getItems().addAll("1. CS2103T Team", "2. CS2106 Lab");
        // dummy values; not fully done
    }
}
