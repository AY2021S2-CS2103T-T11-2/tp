package seedu.address.model.task.repeatable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.Interval;
import seedu.address.model.task.deadline.Deadline;
import seedu.address.testutil.EventBuilder;

public class EventTest {

    private static final Event TUTORIAL = new EventBuilder().withDescription("CS2106 Tutorial")
            .withAtDate(LocalDate.of(2020, 01, 01)).withInterval(Interval.WEEKLY).build();
    private static final Event LAB = new EventBuilder().withDescription("CS2030S Lab")
            .withAtDate(LocalDate.of(2021, 01, 03)).withInterval(Interval.DAILY).build();

    @Test
    public void constructor_null_throwsNullPointerException() {
        LocalDate validDate = LocalDate.of(2020, 1, 1);
        Interval interval = Interval.DAILY;
        assertThrows(NullPointerException.class, () -> new Event(null, interval, validDate));
        assertThrows(NullPointerException.class, () -> new Event("test", null, validDate));
        assertThrows(NullPointerException.class, () -> new Event("test", interval, null));
        assertThrows(NullPointerException.class, () -> new Event(null, interval, false, validDate));
        assertThrows(NullPointerException.class, () -> new Event("test", null,
                false, validDate));
        assertThrows(NullPointerException.class, () -> new Event("test", interval, null, validDate));
        assertThrows(NullPointerException.class, () -> new Event("test", interval, false, null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        LocalDate validDate = LocalDate.of(2020, 1, 1);
        Interval validInterval = Interval.DAILY;
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Event(invalidDescription, validInterval, validDate));
        assertThrows(IllegalArgumentException.class, () -> new Event(invalidDescription, validInterval,
                false, validDate));
        String invalidDescription2 = " ";
        assertThrows(IllegalArgumentException.class, () -> new Event(invalidDescription2, validInterval, validDate));
        assertThrows(IllegalArgumentException.class, () -> new Event(invalidDescription2, validInterval,
                false, validDate));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Deadline.isValidDescription(null));

        // invalid description
        assertFalse(Deadline.isValidDescription("")); // empty string
        assertFalse(Deadline.isValidDescription(" ")); // spaces only

        // valid description
        assertTrue(Deadline.isValidDescription("Tutorial, CS2106, #01-355"));
        assertTrue(Deadline.isValidDescription("-")); // one character
        assertTrue(Deadline.isValidDescription("Lab; Com1; 123456; SINGAPORE"));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event tutorialCopy = new EventBuilder(TUTORIAL).build();
        assertEquals(TUTORIAL, tutorialCopy);

        // same object -> returns true
        assertEquals(TUTORIAL, TUTORIAL);

        // null -> returns false
        assertNotEquals(TUTORIAL, null);

        // different type -> returns false
        assertNotEquals(TUTORIAL, 5);

        // different event -> returns false
        assertNotEquals(LAB, TUTORIAL);

        // different name -> returns false
        Event editedTutorial = new EventBuilder(TUTORIAL).withDescription("NOT TUTORIAL").build();
        assertNotEquals(editedTutorial, TUTORIAL);

        // different interval -> returns false
        editedTutorial = new EventBuilder(TUTORIAL).withInterval(Interval.NONE).build();
        assertNotEquals(editedTutorial, ALICE);

        // different by date -> returns false
        editedTutorial = new EventBuilder(TUTORIAL)
                .withAtDate(LocalDate.of(2019, 01, 01)).build();
        assertNotEquals(editedTutorial, ALICE);

    }
}
