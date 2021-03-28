package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TASK_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddContactToCommand;
import seedu.address.logic.commands.AddDeadlineCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddTodoCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteContactFromCommand;
import seedu.address.logic.commands.DeleteDeadlineCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteProjectCommand;
import seedu.address.logic.commands.DeleteTodoCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListContactsCommand;
import seedu.address.logic.commands.ShowOverviewTabCommand;
import seedu.address.logic.commands.ShowTodayCommand;
import seedu.address.logic.commands.ShowTodosTabCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.task.Interval;
import seedu.address.model.task.deadline.Deadline;
import seedu.address.model.task.repeatable.Event;
import seedu.address.model.task.todo.Todo;
import seedu.address.testutil.DeadlineBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TodoBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addCto() throws Exception {
        Person person = new PersonBuilder().build();
        Index projectIndex = Index.fromOneBased(1);
        AddContactToCommand command = (AddContactToCommand) parser.parseCommand(
                PersonUtil.getAddCtoCommand(projectIndex, person)
        );
        assertEquals(new AddContactToCommand(projectIndex, person), command);
    }

    @Test
    public void parseCommand_addDto_success() throws Exception {
        Deadline deadline = new DeadlineBuilder().withDescription("CS2106 Tutorial")
                .withByDate(LocalDate.of(2020, 01, 01)).build();
        Index projectIndex = Index.fromOneBased(1);
        String addDToCommand = AddDeadlineCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + PREFIX_DESCRIPTION + "CS2106 Tutorial" + " " + PREFIX_DEADLINE_DATE + "01-01-2020";
        AddDeadlineCommand command = (AddDeadlineCommand) parser.parseCommand(addDToCommand);
        assertEquals(new AddDeadlineCommand(projectIndex, deadline), command);
    }

    @Test
    public void parseCommand_addEto_success() throws Exception {
        Event event = new EventBuilder().withDescription("CS2106 Tutorial")
                .withAtDate(LocalDate.of(2020, 01, 01)).withInterval(Interval.WEEKLY).build();
        Index projectIndex = Index.fromOneBased(1);
        String addEToCommand = AddEventCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + PREFIX_DESCRIPTION + "CS2106 Tutorial" + " " + PREFIX_EVENT_INTERVAL + "WEEKLY" + " "
                + PREFIX_EVENT_DATE + "01-01-2020";
        AddEventCommand command = (AddEventCommand) parser.parseCommand(addEToCommand);
        assertEquals(new AddEventCommand(projectIndex, event), command);
    }

    @Test
    public void parseCommand_addTto_success() throws Exception {
        Todo todo = new TodoBuilder().withDescription("CS2106 Tutorial").build();
        Index projectIndex = Index.fromOneBased(1);
        String inputCommand = AddTodoCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + PREFIX_DESCRIPTION + "CS2106 Tutorial";
        AddTodoCommand command = (AddTodoCommand) parser.parseCommand(inputCommand);
        assertEquals(new AddTodoCommand(projectIndex, todo), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteP() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        String deleteProjectCommandLine = DeleteProjectCommand.COMMAND_WORD + " " + projectIndex.getOneBased();
        DeleteProjectCommand command = (DeleteProjectCommand) parser.parseCommand(deleteProjectCommandLine);
        assertEquals(new DeleteProjectCommand(projectIndex), command);
    }

    @Test
    public void parseCommand_deleteCfrom() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        DeleteContactFromCommand command = (DeleteContactFromCommand) parser.parseCommand(
                DeleteContactFromCommand.COMMAND_WORD + " " + projectIndex.getOneBased() + " "
                + PREFIX_REMOVE_TASK_INDEX + " " + projectIndex.getOneBased()
        );
        assertEquals(new DeleteContactFromCommand(INDEX_FIRST, INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteD() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        Index deadlineIndex = Index.fromOneBased(1);

        DeleteDeadlineCommand command = (DeleteDeadlineCommand) parser.parseCommand(
                DeleteDeadlineCommand.COMMAND_WORD + " " + projectIndex.getOneBased() + " "
                        + PREFIX_REMOVE_TASK_INDEX + " " + deadlineIndex.getOneBased()
        );

        assertEquals(new DeleteDeadlineCommand(projectIndex, deadlineIndex), command);
    }

    @Test
    public void parseCommand_deleteT() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        Index todoIndex = Index.fromOneBased(1);

        DeleteTodoCommand command = (DeleteTodoCommand) parser.parseCommand(
                DeleteTodoCommand.COMMAND_WORD + " " + projectIndex.getOneBased() + " "
                        + PREFIX_REMOVE_TASK_INDEX + " " + todoIndex.getOneBased()
        );

        assertEquals(new DeleteTodoCommand(projectIndex, todoIndex), command);
    }

    @Test
    public void parseCommand_deleteE() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        Index eventIndex = Index.fromOneBased(1);

        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + projectIndex.getOneBased() + " "
                        + PREFIX_REMOVE_TASK_INDEX + " " + eventIndex.getOneBased()
        );

        assertEquals(new DeleteEventCommand(projectIndex, eventIndex), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListContactsCommand.COMMAND_WORD) instanceof ListContactsCommand);
        assertTrue(parser.parseCommand(ListContactsCommand.COMMAND_WORD + " 3") instanceof ListContactsCommand);
    }

    @Test
    public void parseCommand_today() throws Exception {
        assertTrue(parser.parseCommand(ShowTodayCommand.COMMAND_WORD) instanceof ShowTodayCommand);
        assertTrue(parser.parseCommand(ShowTodayCommand.COMMAND_WORD + " 3") instanceof ShowTodayCommand);
    }

    @Test
    public void parseCommand_tabO() throws Exception {
        assertTrue(parser.parseCommand(ShowOverviewTabCommand.COMMAND_WORD) instanceof ShowOverviewTabCommand);
        assertTrue(parser.parseCommand(ShowOverviewTabCommand.COMMAND_WORD + " 3")
                instanceof ShowOverviewTabCommand);
    }

    @Test
    public void parseCommand_tabT() throws Exception {
        assertTrue(parser.parseCommand(ShowTodosTabCommand.COMMAND_WORD) instanceof ShowTodosTabCommand);
        assertTrue(parser.parseCommand(ShowTodosTabCommand.COMMAND_WORD + " 3")
                instanceof ShowTodosTabCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
