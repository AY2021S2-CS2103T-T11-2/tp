package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.task.repeatable.Event;

/**
 * Adds an event to a specified project in CoLAB.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits event list of project identified by index.\n"
            + "Existing list will be appended with the input event.\n"
            + "Parameters:\nPROJECT_INDEX\n"
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_REPEATABLE_INTERVAL + "INTERVAL "
            + PREFIX_REPEATABLE_DATE + "REPEATABLE_DATE\n"
            + "Example:\n" + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Project meeting "
            + PREFIX_REPEATABLE_INTERVAL + "DAILY "
            + PREFIX_REPEATABLE_DATE + "24-04-2021";

    public static final String MESSAGE_EVENT_ADDED_SUCCESS = "New event added: %1$s";

    private final Index index;
    private final Event toAdd;


    /**
     * Creates an AddEventCommand to add specified {@code Event} to {@code Project} with {@code Index}.
     *
     * @param index index of {@code Project} to add event in the list.
     * @param event {@code Event} to add.
     */
    public AddEventCommand(Index index, Event event) {
        requireAllNonNull(index, event);

        this.index = index;
        this.toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(index.getZeroBased());
        assert projectToEdit != null;
        projectToEdit.addEvent(toAdd);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_EVENT_ADDED_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
