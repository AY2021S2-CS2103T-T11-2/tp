package seedu.address.model.task.completable;

import seedu.address.model.person.Address;
import seedu.address.model.task.completable.Todo;

import javax.management.Descriptor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class TodoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Todo(null));
        assertThrows(NullPointerException.class, () -> new Todo(null, null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Todo(invalidDescription));
        assertThrows(IllegalArgumentException.class, () -> new Todo(invalidDescription, false));
        String invalidDescription2 = " ";
        assertThrows(IllegalArgumentException.class, () -> new Todo(invalidDescription2));
        assertThrows(IllegalArgumentException.class, () -> new Todo(invalidDescription2, false));
    }

    @Test
    public void isValidDescription() {
        // null address
        assertThrows(NullPointerException.class, () -> Todo.isValidDescription(null));

        // invalid addresses
        assertFalse(Todo.isValidDescription("")); // empty string
        assertFalse(Todo.isValidDescription(" ")); // spaces only

        // valid addresses
        assertTrue(Todo.isValidDescription("Blk 456, Den Road, #01-355"));
        assertTrue(Todo.isValidDescription("-")); // one character
        assertTrue(Todo.isValidDescription("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long description
    }
}