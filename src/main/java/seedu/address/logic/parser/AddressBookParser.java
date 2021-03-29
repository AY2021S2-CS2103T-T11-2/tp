package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddDeadlineCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddGroupmateCommand;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.commands.AddTodoCommand;
import seedu.address.logic.commands.ClearContactCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteDeadlineCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteGroupmateCommand;
import seedu.address.logic.commands.DeleteProjectCommand;
import seedu.address.logic.commands.DeleteTodoCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindContactCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListContactsCommand;
import seedu.address.logic.commands.MarkDeadlineCommand;
import seedu.address.logic.commands.MarkEventCommand;
import seedu.address.logic.commands.MarkTodoCommand;
import seedu.address.logic.commands.ShowOverviewTabCommand;
import seedu.address.logic.commands.ShowTodayCommand;
import seedu.address.logic.commands.ShowTodosTabCommand;
import seedu.address.logic.commands.UpdateContactCommand;
import seedu.address.logic.commands.UpdateDeadlineCommand;
import seedu.address.logic.commands.UpdateEventCommand;
import seedu.address.logic.commands.UpdateGroupmateCommand;
import seedu.address.logic.commands.UpdateProjectCommand;
import seedu.address.logic.commands.UpdateTodoCommand;
import seedu.address.logic.commands.ViewProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddContactCommand.COMMAND_WORD:
            return new AddContactCommandParser().parse(arguments);

        case AddProjectCommand.COMMAND_WORD:
            return new AddProjectCommandParser().parse(arguments);

        case AddGroupmateCommand.COMMAND_WORD:
            return new AddGroupmateCommandParser().parse(arguments);

        case AddDeadlineCommand.COMMAND_WORD:
            return new AddDeadlineCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case AddTodoCommand.COMMAND_WORD:
            return new AddTodoCommandParser().parse(arguments);

        case UpdateContactCommand.COMMAND_WORD:
            return new UpdateContactCommandParser().parse(arguments);

        case DeleteContactCommand.COMMAND_WORD:
            return new DeleteContactCommandParser().parse(arguments);

        case DeleteGroupmateCommand.COMMAND_WORD:
            return new DeleteGroupmateCommandParser().parse(arguments);

        case DeleteProjectCommand.COMMAND_WORD:
            return new DeleteProjectCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case DeleteDeadlineCommand.COMMAND_WORD:
            return new DeleteDeadlineCommandParser().parse(arguments);

        case DeleteTodoCommand.COMMAND_WORD:
            return new DeleteTodoCommandParser().parse(arguments);

        case ClearContactCommand.COMMAND_WORD:
            return new ClearContactCommand();

        case FindContactCommand.COMMAND_WORD:
            return new FindContactCommandParser().parse(arguments);

        case ListContactsCommand.COMMAND_WORD:
            return new ListContactsCommand();

        case MarkDeadlineCommand.COMMAND_WORD:
            return new MarkDeadlineCommandParser().parse(arguments);

        case MarkEventCommand.COMMAND_WORD:
            return new MarkEventCommandParser().parse(arguments);

        case MarkTodoCommand.COMMAND_WORD:
            return new MarkTodoCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UpdateTodoCommand.COMMAND_WORD:
            return new UpdateTodoCommandParser().parse(arguments);

        case UpdateDeadlineCommand.COMMAND_WORD:
            return new UpdateDeadlineCommandParser().parse(arguments);

        case UpdateEventCommand.COMMAND_WORD:
            return new UpdateEventCommandParser().parse(arguments);

        case UpdateProjectCommand.COMMAND_WORD:
            return new UpdateProjectCommandParser().parse(arguments);

        case UpdateGroupmateCommand.COMMAND_WORD:
            return new UpdateGroupmateCommandParser().parse(arguments);

        case ViewProjectCommand.COMMAND_WORD:
            return new ViewProjectCommandParser().parse(arguments);

        case ShowOverviewTabCommand.COMMAND_WORD:
            return new ShowOverviewTabCommand();

        case ShowTodosTabCommand.COMMAND_WORD:
            return new ShowTodosTabCommand();

        case ShowTodayCommand.COMMAND_WORD:
            return new ShowTodayCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
