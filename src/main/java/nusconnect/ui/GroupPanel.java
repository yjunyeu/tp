package nusconnect.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import nusconnect.commons.core.LogsCenter;
import nusconnect.model.group.Group;

/**
 * The UI component that is responsible for containing the list of groups.
 */
public class GroupPanel extends UiPart<Region> {
    private static final String FXML = "GroupPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(GroupPanel.class);

    @FXML
    private ListView<Group> groupListView;

    /**
     * Creates a {@code GroupPanel}.
     */
    public GroupPanel(ObservableList<Group> groupList) {
        super(FXML);
        groupListView.getStyleClass().add("group-list");
        groupListView.setItems(groupList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
    }

    /**
     * Returns the ListView containing groups.
     */
    public ListView<Group> getGroupListView() {
        return groupListView;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Group}.
     */
    class GroupListViewCell extends ListCell<Group> {
        @Override
        protected void updateItem(Group group, boolean empty) {
            super.updateItem(group, empty);

            if (empty || group == null) {
                setGraphic(null);
                setText(null);
            } else {
                int displayIndex = getIndex() + 1;
                setText(displayIndex + ". " + group.getGroupName());
            }
        }
    }
}
