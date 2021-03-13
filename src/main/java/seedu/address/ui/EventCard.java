package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.task.repeatable.Event;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventCard.fxml";

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label eventDescription;
    @FXML
    private Label interval;
    @FXML
    private Label date;

    /**
     * Creates a {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        eventDescription.setText(event.getDescription());
        interval.setText(event.getRecurrence().toString());
        date.setText(DateUtil.decodeDate(event.getAt()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
