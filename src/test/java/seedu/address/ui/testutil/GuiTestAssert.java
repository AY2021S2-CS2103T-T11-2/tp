package seedu.address.ui.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.DateUtil.decodeDateWithDay;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CompletableDeadlineCardHandle;
import guitests.guihandles.CompletableTodoCardHandle;
import guitests.guihandles.ContactCardHandle;
import guitests.guihandles.ContactListPanelHandle;
import guitests.guihandles.EventCardHandle;
import guitests.guihandles.ProjectCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.contact.Contact;
import seedu.address.model.project.Project;
import seedu.address.model.task.CompletableDeadline;
import seedu.address.model.task.CompletableTodo;
import seedu.address.model.task.repeatable.Event;
import seedu.address.ui.CompletableDeadlineCard;
import seedu.address.ui.CompletableTodoCard;

/**
 * @@author {se-edu}-reused
 * Reused from AB4 https://github.com/se-edu/addressbook-level4/
 *
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ContactCardHandle expectedCard, ContactCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedContact}.
     */
    public static void assertCardDisplaysContact(Contact expectedContact, ContactCardHandle actualCard) {
        assertEquals(expectedContact.getName().fullName, actualCard.getName());
        assertEquals(expectedContact.getPhone().value, actualCard.getPhone());
        assertEquals(expectedContact.getEmail().value, actualCard.getEmail());
        assertEquals(expectedContact.getAddress().value, actualCard.getAddress());
        assertEquals(expectedContact.getTags().stream().map(tag -> tag.tagName).sorted().collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCompletableDeadline}.
     */
    public static void assertCardDisplaysCompletableDeadline(
            CompletableDeadline expectedDeadline, CompletableDeadlineCardHandle actualCard) {
        assertEquals(expectedDeadline.getDescription(), actualCard.getDescription());
        assertEquals(decodeDateWithDay(expectedDeadline.getBy()), actualCard.getDate());
        String expectedCompletedText = CompletableDeadlineCard
                .getTextToDisplay(expectedDeadline.getIsDone());
        assertEquals(expectedCompletedText, actualCard.getCompleted());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCompletableTodo}.
     */
    public static void assertCardDisplaysCompletableTodo(
            CompletableTodo expectedTodo, CompletableTodoCardHandle actualCard) {
        assertEquals(expectedTodo.getDescription(), actualCard.getDescription());
        String expectedCompletedText = CompletableTodoCard
                .getTextToDisplay(expectedTodo.getIsDone());
        assertEquals(expectedCompletedText, actualCard.getCompleted());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEvent}.
     */
    public static void assertCardDisplaysEvent(Event expectedEvent, EventCardHandle actualCard) {
        assertEquals(expectedEvent.getDescription(), actualCard.getDescription());
        assertEquals(expectedEvent.getRecurrence().toString(), actualCard.getInteval());
        assertEquals(decodeDateWithDay(expectedEvent.getAt()), actualCard.getDate());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedProject}.
     */
    public static void assertCardDisplaysProject(Project expectedProject, ProjectCardHandle actualCard) {
        assertEquals(expectedProject.getProjectName().toString(), actualCard.getName());
    }

    /**
     * Asserts that the list in {@code contactListPanelHandle} displays the details of {@code contacts} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ContactListPanelHandle contactListPanelHandle, Contact... contacts) {
        for (int i = 0; i < contacts.length; i++) {
            contactListPanelHandle.navigateToCard(i);
            assertCardDisplaysContact(contacts[i], contactListPanelHandle.getContactCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code contactListPanelHandle} displays the details of {@code contacts} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ContactListPanelHandle contactListPanelHandle, List<Contact> contacts) {
        assertListMatching(contactListPanelHandle, contacts.toArray(new Contact[0]));
    }

    /**
     * Asserts the size of the list in {@code contactListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ContactListPanelHandle contactListPanelHandle, int size) {
        int numberOfPeople = contactListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
