package nusconnect.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import nusconnect.model.group.Group;

/**
 * A UI component that displays information of a {@code Group}.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupCard.fxml";

    public final Group group;

    @FXML
    private VBox cardPane;

    @FXML
    private Label groupName;

    @FXML
    private Label id;

    @FXML
    private VBox membersBox;

    /**
     * Creates a {@code GroupCard} with the given {@code Group} and index to display.
     */
    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        groupName.setText(group.getGroupName());

        // If there are any member add it and sort by name
        if (!group.getMembers().isEmpty()) {
            group.getMembers().stream()
                    .sorted(Comparator.comparing(person -> person.getName().fullName))
                    .forEach(member -> {
                        Label memberLabel = new Label("• " + member.getName().fullName);
                        memberLabel.getStyleClass().add("member-label");
                        membersBox.getChildren().add(memberLabel);
                    });
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupCard)) {
            return false;
        }

        GroupCard card = (GroupCard) other;
        return id.getText().equals(card.id.getText())
                && group.equals(card.group);
    }
}
