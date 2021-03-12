package seedu.address.model.task.repeatable;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.task.Interval;
import seedu.address.model.task.Repeatable;

/**
 * Represents a Repeatable as an Event.
 */
public class Event extends Repeatable {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Description should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String MESSAGE_INTERVAL_CONSTRAINTS =
            "Interval should only contain enum values like: NONE, DAILY, WEEKLY, FORTNIGHTLY, MONTHLY, YEARLY";
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be in the form DD-MM-YYYY";
    /**
     * Constructor for Event.
     * @param description Description of the Event.
     * @param at Date of the Event.
     */
    public Event(String description, Interval interval, LocalDate at) {
        super(description, interval, at);
    }

    /**
     * Constructor for Event.
     * @param description Description of the Event.
     * @param isDone Marks whether the Event is Done.
     * @param at Date of the Event.
     */
    public Event(String description, Interval interval, Boolean isDone, LocalDate at) {
        super(description, interval, at, isDone);
    }

    /**
     * Returns the Event's interval.
     * @return Interval interval.
     */
    public Interval getRecurrence() {
        return this.interval;
    }

    /**
     * Sets the Event's interval to specified level.
     * @param interval Level of Interval.
     */
    public void setRecurrence(Interval interval) {
        assert interval != null;
        this.interval = interval;
    }

    /**
     * Checks if an instance of a Event is equal to another Object.
     * @param other Object to be compared with.
     * @return True if both objects are equal. Else return false.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getDescription().equals(getDescription())
                && otherEvent.getIsDone().equals(getIsDone());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, isDone, at);
    }

    /**
     * Returns a String representation of the Event.
     * @return A String representation of the Event.
     */
    @Override
    public String toString() {
        return "[E]" + this.getStatusIcon() + " " + this.description + " (at: " + DateUtil.decodeDate(at) + ")";
    }
}
