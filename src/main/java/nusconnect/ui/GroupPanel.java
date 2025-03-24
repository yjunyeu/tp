package nusconnect.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import nusconnect.commons.core.LogsCenter;

/**
 * The UI component that is responsible for containing the list of groups.
 */
public class GroupPanel extends UiPart<Region> {
    private static final String FXML = "GroupPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(GroupPanel.class);

    // Observable list to store group items
    private static ObservableList<String> groupItems = FXCollections.observableArrayList();

    @FXML
    private ListView<String> groupListView;

    /**
     * Creates a {@code GroupPanel}.
     */
    public GroupPanel() {
        super(FXML);
        groupListView.getStyleClass().add("group-list");
        groupListView.setItems(groupItems);
    }

    /**
     * Adds a group to the list.
     */
    public static void addGroup(String name) {
        logger.info("Adding group: " + name);

        // Add the new group with the next sequential number
        int nextNumber = groupItems.size() + 1;
        groupItems.add(nextNumber + ". " + name);
    }
}